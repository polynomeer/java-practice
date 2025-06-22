package javaclass.superkeyword;

public class PlaneTest {
    public static void main(String[] args) {
        FighterPlane fighterPlane = new FighterPlane();
        fighterPlane.takeOff();
        fighterPlane.fly();
        fighterPlane.flyMode = fighterPlane.SUPERSONIC;
        fighterPlane.fly();
        fighterPlane.land();
    }
}
