package javajungsuk;

import java.awt.*;
import java.awt.event.*;
class TextFieldTest2 extends Frame {
    Label lid;
    Label lpwd;
    TextField tfId;
    TextField tfPwd;
    Button ok;
    TextFieldTest2(String title) {
        super(title); // Frame(String title) . 을 호출한다
        lid = new Label("ID :", Label.RIGHT); // Label text . 의 정렬을 오른쪽으로
        lpwd = new Label("Password :", Label.RIGHT);
// 10 TextField . 약 개의 글자를 입력할 수 있는 생성
        tfId = new TextField(10);
        tfPwd = new TextField(10);
        tfPwd.setEchoChar('*'); // '*' . 입력한 값 대신 이 보이게 한다
        ok = new Button("OK");
// OK TextField Listener . 버튼과 에 이벤트처리를 위한 를 추가해준다
        tfId.addActionListener(new EventHandler());
        tfPwd.addActionListener(new EventHandler());
        ok.addActionListener(new EventHandler());
        setLayout(new FlowLayout()); // LayoutManager FlowLayout 를 으로
        add(lid); // Component Frame . 생성한 들을 에 포함시킨다
        add(tfId);
        add(lpwd);
        add(tfPwd);
        add(ok);
        setSize(450, 65);
        setVisible(true); // Frame . 이 화면에 보이게 한다
    }
    public static void main(String args[]) {
        TextFieldTest2 f = new TextFieldTest2("Login");
    }
    class EventHandler implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String id = tfId.getText(); // tfId text . 에 입력되어있는 를 얻어온다
            String password = tfPwd.getText();
            if (!id.equals("javachobo")){
                System.out.println("입력하신 id가 유효하지 않습니다. "
                        + "다시 입력해 주세요 .");
// id , focus tfId . 를 다시 입력할 수 있도록 를 로 옮긴다
                tfId.requestFocus();
                tfId.selectAll(); // tfId text . 에 입력된 가 선택되게 한다
            } else if (!password.equals("asdf")) {
                System.out.println(" 입력하신 비밀번호가 틀렸습니다. "
                        + " .");
                // id focus tfId . 를 다시 입력할 수 있도록 를 로 옮긴다
                tfPwd.requestFocus();
                tfPwd.selectAll();
            } else {
                System.out.println( id + " 님, 성공적으로 로그인 되었습니다 .");
            }
        }
    } // class javajungsuk.EventHandler
}