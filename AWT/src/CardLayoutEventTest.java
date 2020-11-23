import java.awt.*;
import java.awt.event.*;
class CardLayoutEventTest extends Frame {
    Button first, prev, next, last;
    Panel buttons;
    Panel slide;
    Panel card1, card2, card3, card4, card5; // slide Panel 에 포함될 들
    CardLayout card;
    CardLayoutEventTest(String title) {
        super(title); // Frame Frame(String title) 조상인 의 생성자 을 호출
// Panel . 화면을 담을 을 담는다
        slide = new Panel();
        card = new CardLayout();
        slide.setLayout(card);
// Panel . 버튼을 담을 을 만든다
        buttons = new Panel();
        buttons.setLayout(new FlowLayout());
        first = new Button("<<");
        prev = new Button("<");
        next = new Button(">");
        last = new Button(">>");
        buttons.add(first);
        buttons.add(prev);
        buttons.add(next);
        buttons.add(last);
// . 버튼에 이벤트 리스너를 추가한다
        first.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
// CardLayout slide(Panel) . 의 첫 번째 이 보이도록 한다
                card.first(slide);
            }
        });
        prev.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
// slide slide(Panel) .
                card.previous(slide);
            }
        });
        next.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
// slide slide(Panel) . 현재 의 다음 이 보이도록 한다
                card.next(slide);
            }
        });
        last.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
// CardLayout slide(Panel) . 의 마지막 이 보이도록 한다
                card.last(slide);
            }
        });
        card1= new Panel();
        card1.setBackground(Color.gray);
        card1.add(new Label(" 첫 번째 페이지입니다."));
                card2= new Panel();
        card2.add(new Label(" 두 번째 페이지입니다."));
        card2.setBackground(Color.orange);
        card3= new Panel();
        card3.add(new Label(" 세 번째 페이지입니다."));
        card3.setBackground(Color.blue);
        card4= new Panel();
        card4.add(new Label(" 네 번째 페이지입니다."));
        card4.setBackground(Color.cyan);
        card5= new Panel();
        card5.add(new Label(" 다섯 번째 페이지입니다."));
        card5.setBackground(Color.pink);
// slide(Panel) card1(Panel) "1" . 에 을 이란 이름으로 추가한다
        slide.add(card1, "1");
        slide.add(card2, "2");
        slide.add(card3, "3");
        slide.add(card4, "4");
        slide.add(card5, "5");
        add("South", buttons);
        add("Center", slide);
        setSize(200, 200);
        setLocation(200, 200);
        setVisible(true);
        card.show(slide,"1"); // (card1) . 첫 번째 슬라이드 가 나타나게 한다
    }
    public static void main(String args[]) {
        CardLayoutEventTest mainWin =
                new CardLayoutEventTest("CardLayoutTest");
    }
}