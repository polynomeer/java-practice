package com.polynomeer.deadlock;

import java.util.concurrent.ThreadLocalRandom;

public class LiveLockResolvedDemo {

    static class Worker {
        private final String name;
        private final int priority; // 숫자가 작을수록 우선
        private volatile boolean active;

        public Worker(String name, int priority, boolean active) {
            this.name = name;
            this.priority = priority;
            this.active = active;
        }

        public String getName() {
            return name;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        // 우선순위 비교(같으면 이름으로 안정적인 타이브레이크)
        public boolean hasPriorityOver(Worker other) {
            if (this.priority != other.priority) {
                return this.priority < other.priority;
            }
            return this.name.compareTo(other.name) < 0;
        }

        public void work(SharedResource resource, Worker other) {
            int safety = 0; // 데모용 세이프가드
            while (active && safety++ < 1_000_000) {
                if (resource.getOwner() != this) {
                    tinyJitter();              // 경쟁을 조금 완화
                    Thread.yield();
                    continue;
                }

                if (other.isActive()) {
                    // ★ 라이브락 해소 포인트 ★
                    // 둘 다 활성이라도, 나는 "우선권이 있을 때는" 양보하지 않고 진행한다.
                    if (!this.hasPriorityOver(other)) {
                        System.out.println(name + " : " + other.getName() + "에게 양보(내 우선순위 낮음)");
                        resource.setOwner(other);
                        tinyJitter();
                        continue;
                    }
                    // 나는 우선순위가 높다 → 양보하지 말고 작업 수행
                    System.out.println(name + " : 우선권 보유 → 작업 진행");
                }

                // 실제 작업 구간(진행 발생)
                doWorkBriefly();

                System.out.println(name + " : 작업 완료 ✅");
                active = false;

                // 다음 진행을 위해 소유권 인계
                resource.setOwner(other);
                tinyJitter();
            }

            if (active) {
                System.out.println(name + " : 안전종료(시간/루프 제한)");
                active = false;
            }
        }

        private static void tinyJitter() {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 4)); // 1~3ms
            } catch (InterruptedException ignored) {
            }
        }

        private static void doWorkBriefly() {
            try {
                Thread.sleep(5); // 작업을 흉내 내는 짧은 블록
            } catch (InterruptedException ignored) {
            }
        }
    }

    static class SharedResource {
        private Worker owner;

        public SharedResource(Worker owner) {
            this.owner = owner;
        }

        public Worker getOwner() {
            return owner;
        }

        public void setOwner(Worker owner) {
            this.owner = owner;
        }
    }

    public static void main(String[] args) throws Exception {
        // 우선순위로 대칭을 깨서 '동시 양보' 패턴을 차단
        Worker w1 = new Worker("스레드1", /*priority=*/1, true);
        Worker w2 = new Worker("스레드2", /*priority=*/2, true);

        SharedResource resource = new SharedResource(w1);

        Thread t1 = new Thread(() -> w1.work(resource, w2));
        Thread t2 = new Thread(() -> w2.work(resource, w1));

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("끝.");
    }
}

