package javajungsuk;

import java.awt.*;
class ScrollPaneTest {
    public static void main(String args[]) {
        Frame f = new Frame("javajungsuk.ScrollPaneTest");
        f.setSize(300, 200);
        ScrollPane sp = new ScrollPane();
        Panel p = new Panel();
        p.setBackground(Color.green); // Panel green . 의 배경을 으로 한다
        p.add(new Button("첫번째"));  // Button Panel . 을 에 포함시킨다
        p.add(new Button("두번째"));
        p.add(new Button("세번째"));
        p.add(new Button("네번째"));
        sp.add(p); // Panel ScrollPane . 을 에 포함시킨다
        f.add(sp); // ScrollPane Frame . 을 에 포함시킨다
        f.setVisible(true);
    }
}