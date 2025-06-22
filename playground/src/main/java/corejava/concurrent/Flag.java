package corejava.concurrent;

public class Flag {
    private boolean done;

    public synchronized void set() {
        done = true;
    }

    public synchronized boolean get() {
        return done;
    }
}