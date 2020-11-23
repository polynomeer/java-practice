package javajungsuk;

import java.awt.*;
class ButtonTest2 {
    public static void main(String args[]) {
        Frame f = new Frame("Login");
        f.setSize(300, 200);
        f.setLayout(null);                  // 레이아웃 매니저의 설정을 해제한다
        Button b = new Button("check");
        b.setSize(100, 50);    // Button 의 크기를 설정한다
        b.setLocation(100, 75);      // Frame 내에서의 Button 의 위치를 설정한다
        f.add(b);
        f.setVisible(true);
    }
}