package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Sample text");
        JButton btn1 = new JButton("Click me");
        JButton btn2 = new JButton("Exit");
        JTextArea textArea = new JTextArea(); // this is useful to write plenty text
        JTextField textField = new JTextField(200);
        JPanel btnPanel = new JPanel();

        panel.setLayout(new BorderLayout()); // BorderLayout make you can set layout

        btnPanel.add(btn1);
        btnPanel.add(btn2);
        panel.add(label, BorderLayout.NORTH);
        panel.add(btnPanel, BorderLayout.WEST);
        panel.add(textArea, BorderLayout.CENTER);

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText(textArea.getText());
//                textArea.append("Congratulations!!!\n");
            }
        });

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.add(panel); // add panel into frame

        frame.setResizable(false);
        frame.setVisible(true); // frame set false by default
        frame.setPreferredSize(new Dimension(840, 840 / 12 * 9)); // Dimension set width and height to display
        frame.setSize(840, 840 / 12 * 9);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set behavior when the program closed


    }
}
