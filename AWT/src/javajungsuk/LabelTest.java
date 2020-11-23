package javajungsuk;

import java.awt.*;
class LabelTest {
    public static void main(String args[]) {
        Frame f = new Frame("Login");
        f.setSize(300, 200);
        f.setLayout(null);

        Label id = new Label("ID :"); // Label . 을 생성하고 크기와 위치를 지정한다
        id.setBounds(50, 50, 30, 10); // 50, 50 30, 10 위치에 크기가 가로 세로

        Label pwd = new Label("Password :");
        pwd.setBounds(50, 65, 100, 10);

        f.add(id); // Label Frame . 생성한 을 에 포함시킨다
        f.add(pwd);
        f.setVisible(true);
    }
}