package corejava.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

/**
 * 애너테이션은 요소라는 key/value 쌍을 포함할 수 있다.
 * ⎷ 요소의 종류: 기본타입값, Class 객체, enum 인스턴스, 애너테이션, 이들의 배열
 * ⎷ 애너테이션 요소는 절대로 null값을 가질 수 없다.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Repeatable(BugReports.class)
public @interface BugReport {
    boolean showStopper() default false;

    String assignedTo() default "";

    Class<?> testCase() default Void.class;

    Status status() default Status.REPORTED;

    String[] reportedBy() default {};

    Reference ref() default @Reference(id = 0);

    enum Status {REPORTED, CONFIRMED, ASSIGNED, FIXED, WONTFIX}

}

@Target({ElementType.TYPE, ElementType.METHOD})
@interface BugReports {
    BugReport[] value();
}