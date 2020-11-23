import java.awt.*;
import java.awt.event.*; // . 이벤트처리를 위해서 추가해야한다
class DialogTest2 {
    public static void main(String args[]) {
        Frame f = new Frame("Parent");
        f.setSize(300, 200);
// parent Frame f , modal true Dialog . 을 로 하고 을 로 해서 필수응답 로 함
        final Dialog info = new Dialog(f, "Information", true);
        info.setSize(140, 90);
        info.setLocation(50, 50); // parent Frame , 이 아닌 화면기준의 위치
        info.setLayout(new FlowLayout());
        Label msg = new Label("This is modal Dialog", Label.CENTER);
        Button ok = new Button("OK");
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { //OK . 버튼을 누르면 수행됨
// info.setVisible(false); // Dialog . 를 안보이게 한다
                info.dispose(); // Dialog . 를 메모리에서 없앤다
            }
        });
        info.add(msg);
        info.add(ok);
        f.setVisible(true);
        info.setVisible(true); // Dialog . 를 화면에 보이게 한다
    }
}