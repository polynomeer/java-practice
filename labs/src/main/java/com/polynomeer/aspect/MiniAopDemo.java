package com.polynomeer.aspect;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Predicate;

/* ===== 1) 어노테이션(포인트컷 용) ===== */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Loggable {
}

/* ===== 2) AOP 핵심 SPI(간이 버전) ===== */
interface MethodInvocation {
    Method getMethod();

    Object[] getArguments();

    Object getThis();

    Object proceed() throws Throwable;
}

interface MethodInterceptor {
    Object invoke(MethodInvocation invocation) throws Throwable;
}

/* ===== 3) 내부 구현: 호출 진행자 & 어드바이스 체인 ===== */
class ReflectiveMethodInvocation implements MethodInvocation {
    private final Object target;
    private final Method method;
    private final Object[] args;
    private final List<MethodInterceptor> chain;
    private int index = -1;

    ReflectiveMethodInvocation(Object target, Method method, Object[] args, List<MethodInterceptor> chain) {
        this.target = target;
        this.method = method;
        this.args = args != null ? args : new Object[0];
        this.chain = chain != null ? chain : List.of();
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return args;
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public Object proceed() throws Throwable {
        index++;
        if (index < chain.size()) {
            return chain.get(index).invoke(this);
        }
        // 체인이 끝나면 실제 타깃 메서드 호출
        return method.invoke(target, args);
    }
}

/* ===== 4) 포인트컷 + 어드바이저 ===== */
interface Advisor {
    boolean matches(Method m);

    MethodInterceptor interceptor();
}

class PredicatePointcutAdvisor implements Advisor {
    private final Predicate<Method> predicate;
    private final MethodInterceptor interceptor;

    PredicatePointcutAdvisor(Predicate<Method> predicate, MethodInterceptor interceptor) {
        this.predicate = predicate;
        this.interceptor = interceptor;
    }

    @Override
    public boolean matches(Method m) {
        return predicate.test(m);
    }

    @Override
    public MethodInterceptor interceptor() {
        return interceptor;
    }

    // 편의 생성자: 어노테이션 기반 매칭
    static PredicatePointcutAdvisor forAnnotation(Class<? extends Annotation> ann, MethodInterceptor i) {
        return new PredicatePointcutAdvisor(m -> m.isAnnotationPresent(ann), i);
    }
}

/* ===== 5) 예시 어드바이스들 ===== */
// 로깅
class LoggingInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation inv) throws Throwable {
        long t0 = System.nanoTime();
        System.out.println("[LOG] -> " + sig(inv));
        try {
            Object ret = inv.proceed();
            long t1 = System.nanoTime();
            System.out.println("[LOG] <- " + inv.getMethod().getName() + " returned: " + ret + " (" + (t1 - t0) / 1_000_000 + "ms)");
            return ret;
        } catch (Throwable ex) {
            System.out.println("[LOG] !! " + inv.getMethod().getName() + " threw: " + ex);
            throw ex;
        }
    }

    private String sig(MethodInvocation inv) {
        return inv.getMethod().getName() + "(" + Arrays.toString(inv.getArguments()) + ")";
    }
}

// 트랜잭션 흉내 (시뮬레이션)
class TransactionInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation inv) throws Throwable {
        System.out.println("[TX] begin");
        try {
            Object ret = inv.proceed();
            System.out.println("[TX] commit");
            return ret;
        } catch (Throwable ex) {
            System.out.println("[TX] rollback due to " + ex.getClass().getSimpleName());
            throw ex;
        }
    }
}

/* ===== 6) 프록시 팩토리 (JDK 동적 프록시) ===== */
class ProxyFactory {

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(T target, List<Advisor> advisors, Class<?>... interfaces) {
        Objects.requireNonNull(target, "target");
        ClassLoader cl = target.getClass().getClassLoader();
        Class<?>[] ifaces = (interfaces == null || interfaces.length == 0)
                ? target.getClass().getInterfaces()
                : interfaces;

        InvocationHandler handler = (proxy, method, args) -> {
            // Object 기본 메서드는 바로 위임
            if (method.getDeclaringClass() == Object.class) {
                return method.invoke(target, args);
            }
            // 이 메서드에 매칭되는 어드바이스만 체인으로 구성
            List<MethodInterceptor> chain = new ArrayList<>();
            for (Advisor ad : advisors) {
                if (ad.matches(method)) chain.add(ad.interceptor());
            }
            // 체인이 비어도 동일 경로로 호출 (오버헤드 최소화)
            return new ReflectiveMethodInvocation(target, method, args, chain).proceed();
        };

        return (T) Proxy.newProxyInstance(cl, ifaces, handler);
    }
}

/* ===== 7) 비즈니스 코드 ===== */
interface UserService {
    @Loggable
    String createUser(String name);

    String findUser(long id);

    @Loggable
    void deleteUser(long id);
}

class UserServiceImpl implements UserService {
    private final Map<Long, String> store = new HashMap<>();
    private long seq = 0;

    @Override
    public String createUser(String name) {
        long id = ++seq;
        store.put(id, name);
        return "User#" + id;
    }

    @Override
    public String findUser(long id) {
        return store.getOrDefault(id, "N/A");
    }

    @Override
    public void deleteUser(long id) {
        if (store.remove(id) == null) throw new IllegalArgumentException("no such user: " + id);
    }
}

/* ===== 8) 실행 예시 ===== */
public class MiniAopDemo {
    public static void main(String[] args) {
        UserService target = new UserServiceImpl();

        // @Loggable 메서드에만 두 어드바이스(로깅, 트랜잭션)를 적용
        List<Advisor> advisors = List.of(
                PredicatePointcutAdvisor.forAnnotation(Loggable.class, new LoggingInterceptor()),
                PredicatePointcutAdvisor.forAnnotation(Loggable.class, new TransactionInterceptor())
        );

        UserService proxy = ProxyFactory.createProxy(target, advisors, UserService.class);

        String id = proxy.createUser("Jacob");
        System.out.println("CREATED: " + id);
        System.out.println("FIND: " + proxy.findUser(1)); // @Loggable 아님 → 어드바이스 미적용
        System.out.println("--------------------------");

        try {
            proxy.deleteUser(99); // 존재하지 않아 예외 → TX 롤백/로그 출력
        } catch (Exception ignore) {
        }
        System.out.println("--------------------------");

        proxy.deleteUser(1);     // 정상 삭제 → TX 커밋/로그 출력
    }
}
