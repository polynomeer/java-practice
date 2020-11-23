package javajungsuk;

import java.awt.*;
import java.awt.event.*;
class CardLayoutTest {
    public static void main(String args[]) {
        final Frame f = new Frame("javajungsuk.CardLayoutTest");
        final CardLayout card = new CardLayout(10, 10);
        f.setLayout(card);
        Panel card1= new Panel();
        card1.setBackground(Color.lightGray);
        card1.add(new Label("Card 1"));
        Panel card2= new Panel();
        card2.add(new Label("Card 2"));
        card2.setBackground(Color.orange);
        Panel card3= new Panel();
        card3.add(new Label("Card 3"));
        card3.setBackground(Color.cyan);
        f.add(card1, "1"); // Frame card1 "1" . 에 을 이라고 이름 붙여 추가한다
        f.add(card2, "2");
        f.add(card3, "3");
        class Handler extends MouseAdapter {
            public void mouseClicked(MouseEvent e) {
// 마우스 오른쪽 버튼을 눌렀을 때
                if(e.getModifiers() == e.BUTTON3_MASK) {
                    card.previous(f); // CardLayout Panel . 의 이전 을 보여준다
                } else {
                    card.next(f); // CardLayout Panel . 의 다음 을 보여준다
                }
            }
        } // class Handler
        card1.addMouseListener(new Handler());
        card2.addMouseListener(new Handler());
        card3.addMouseListener(new Handler());
        f.setSize(200, 200);
        f.setLocation(200, 200);
        f.setVisible(true);
        card.show(f,"1"); // Frame Component "1" . 에 추가된 중 이름이 인 것을 보여준다
    }
}