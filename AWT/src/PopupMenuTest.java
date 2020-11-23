import java.awt.*;
import java.awt.event.*; // . 이벤트 처리를 위해서 추가했다
class PopupMenuTest {
    public static void main(String args[]) {
        final Frame f = new Frame("PopupMenu Test");
        f.setSize(300, 200);
        final PopupMenu pMenu = new PopupMenu("Edit");
        MenuItem miCut = new MenuItem("Cut");
        MenuItem miCopy = new MenuItem("Copy");
        MenuItem miPaste = new MenuItem("Paste");
        pMenu.add(miCut); // PopupMenu MenuItem . 에 들을 추가한다
        pMenu.add(miCopy);
        pMenu.add(miPaste);
        f.add(pMenu); // PopupMenu Frame . 를 에 추가한다
        f.addMouseListener( new MouseAdapter() { // 익명클래스
            public void mousePressed(MouseEvent me) {
// PopupMenu . 오른쪽 마우스버튼을 누르면 를 화면에 보여준다
                if(me.getModifiers() == me.BUTTON3_MASK)
                    pMenu.show(f, me.getX(), me.getY());
            }
        });
        f.setVisible(true);
    }
}