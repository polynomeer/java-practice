
public class ThreadExample {

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start(); // myThread 실행

        System.out.println("Hello");
    }

    static class MyThread extends Thread {
        @Override
        public void run(){
            System.out.println("Thread: " + Thread.currentThread().getName());
        }
    }
}
