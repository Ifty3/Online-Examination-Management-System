import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Online Examination Management System");
        frame.setSize(1000, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Temp obj = new Temp();

        JButton loginButton = new JButton("Login");
        JButton signUpButton = new JButton("Sign Up");
        JButton forgotButton = new JButton("Forgot Password");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                obj.login(frame);
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                obj.signUp(frame);
            }
        });

        forgotButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                obj.forgot(frame);
            }
        });

        JPanel panel = new JPanel();
        panel.add(loginButton);
        panel.add(signUpButton);
        panel.add(forgotButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}

