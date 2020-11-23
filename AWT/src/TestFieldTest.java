import java.awt.*;

class TextFieldTest {
    public static void main(String args[]) {
        Frame f = new Frame("Login");
        f.setSize(400, 65);
        f.setLayout(new FlowLayout()); // LayoutManager FlowLayout . 를 으로 한다
        Label lid = new Label("ID :", Label.RIGHT); // . 정렬을 오른쪽으로
        Label lpwd = new Label("Password :", Label.RIGHT);
        TextField id = new TextField(10);//약 개의 글자를 입력할 수 있는 생성 10 TextField
        TextField pwd = new TextField(10);
        pwd.setEchoChar('*'); // '*' . 입력한 값 대신 가 보이도록 한다
        f.add(lid); // Frame . 생성한 컴포넌트들을 에 포함시킨다
        f.add(id);
        f.add(lpwd);
        f.add(pwd);
        f.setVisible(true);
    }
}