import java.awt.*;
class CanvasTest {
    public static void main(String args[]) {
        Frame f = new Frame("CanvasTest");
        f.setSize(300, 200);
        f.setLayout(null); // Frame Layout Manager . 의 설정을 해제한다
        Canvas c = new Canvas();
        c.setBackground(Color.pink); // Canvas (pink) . 의 배경을 분홍색 으로 한다
        c.setBounds(50, 50, 150, 100);
        f.add(c); // Canvas Frame . 을 에 포함시킨다
        f.setVisible(true);
    }
}