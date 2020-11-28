package swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogIn extends JFrame {
    public LogIn() {
        JPanel panel = new JPanel();
        JLabel idLabel = new JLabel("ID");
        JLabel pwLabel = new JLabel("Password");
        JTextField txtID = new JTextField(10);
        JPasswordField txtPass = new JPasswordField(10);
        JButton loginBtn = new JButton("Log In");

        panel.add(idLabel);
        panel.add(txtID);
        panel.add(pwLabel);
        panel.add(txtPass);
        panel.add(loginBtn);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = "jacob";
                String pass = "1234";

                if (id.equals(txtID.getText()) && pass.equals(txtPass.getText())) {
                    JOptionPane.showMessageDialog(null, "You have logged in successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to login..");
                }
            }
        });

        add(panel);

        setVisible(true);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new LogIn();
    }
}
