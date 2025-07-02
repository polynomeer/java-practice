package com.polynomeer.gc;

import java.util.*;

public class ZgcObject {
    public enum Color { WHITE, GRAY, BLACK }

    private final String name;
    private final List<ZgcObject> references = new ArrayList<>();
    private Color color = Color.WHITE;

    public ZgcObject(String name) {
        this.name = name;
    }

    public void addReference(ZgcObject obj) {
        references.add(obj);
    }

    public List<ZgcObject> getReferences() {
        return references;
    }

    public void setColor(Color c) {
        this.color = c;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return name + "(" + color + ")";
    }
}
