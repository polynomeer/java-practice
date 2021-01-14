package javaclass.superkeyword;

public class FighterPlane extends Airplane {
    public static final int NORMAL = 1;
    public static final int SUPERSONIC = 2;

    public int flyMode = NORMAL;

    @Override
    public void fly() {
        if (flyMode == SUPERSONIC) {
            System.out.println("supersonic flying...");
        } else {
            // Airplane.fly() 호출
            super.fly();
        }
    }
}
