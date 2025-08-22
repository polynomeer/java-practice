package com.polynomeer.deadlock;

class LiveLockDemo {
    static class Worker {
        private String name;
        private boolean active;

        public Worker(String name, boolean active) {
            this.name = name;
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

        public void work(SharedResource resource, Worker other) {
            while (active) {
                if (resource.getOwner() != this) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                    }
                    continue;
                }
                if (other.isActive()) {
                    System.out.println(name + " : " + other.getName() + "에게 자원 양보");
                    resource.setOwner(other);
                    continue;
                }
                // 작업 수행
                System.out.println(name + " : 작업 완료!");
                active = false;
                resource.setOwner(other);
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

    public static void main(String[] args) {
        Worker w1 = new Worker("스레드1", true);
        Worker w2 = new Worker("스레드2", true);
        SharedResource resource = new SharedResource(w1);

        new Thread(() -> w1.work(resource, w2)).start();
        new Thread(() -> w2.work(resource, w1)).start();
    }
}
