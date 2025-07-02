package com.polynomeer.gc;

import java.util.*;

public class Region {
    enum Type { YOUNG, OLD }
    private final List<GcObject> objects = new ArrayList<>();
    private final Type type;
    private final String id;

    public Region(String id, Type type) {
        this.id = id;
        this.type = type;
    }

    public void addObject(GcObject obj) {
        objects.add(obj);
    }

    public List<GcObject> getObjects() {
        return objects;
    }

    public Type getType() {
        return type;
    }

    public void sweepUnmarked() {
        objects.removeIf(obj -> !obj.isMarked());
        for (GcObject obj : objects) {
            obj.unmark();
        }
    }

    @Override
    public String toString() {
        return "[" + id + "][" + type + "] " + objects;
    }
}
