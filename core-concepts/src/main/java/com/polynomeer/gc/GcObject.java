package com.polynomeer.gc;

import java.util.*;

public class GcObject {
    private final String name;
    private boolean marked;
    private final List<GcObject> references;

    public GcObject(String name) {
        this.name = name;
        this.marked = false;
        this.references = new ArrayList<>();
    }

    public void addReference(GcObject obj) {
        references.add(obj);
    }

    public void removeReference(GcObject obj) {
        references.remove(obj);
    }

    public void mark() {
        if (marked) return;
        marked = true;
        for (GcObject ref : references) {
            ref.mark();
        }
    }

    public boolean isMarked() {
        return marked;
    }

    public void unmark() {
        marked = false;
    }

    public List<GcObject> getReferences() {
        return references;
    }

    @Override
    public String toString() {
        return name;
    }
}
