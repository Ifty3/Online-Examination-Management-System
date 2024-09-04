import javax.swing.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

class Temp {
    private String userName, email, password;
    private String searchName, searchPass, searchEmail;
    private List<Question> questions;

    public Temp() {
        questions = loadQuestionsFromFile();
    }

    public void login(JFrame frame) {
        JTextField userField = new JTextField(20);
        JPasswordField passField = new JPasswordField(20);
        Object[] message = {
                "Username:", userField,
                "Password:", passField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            searchName = userField.getText();
            searchPass = new String(passField.getPassword());

            try {
                File file = new File("loginData.txt");
                if (!file.exists()) {
                    throw new FileNotFoundException("File 'loginData.txt' not found");
                }
                Scanner fileScanner = new Scanner(file);
                boolean found = false;
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] data = line.split("\\*");
                    userName = data[0];
                    email = data[1];
                    password = data[2];
                    if (userName.equals(searchName) && password.equals(searchPass)) {
                        found = true;
                        // Show appropriate panel based on user role
                        if (userName.equals("admin")) {
                            showTeacherPanel();
                        } else {
                            showStudentPanel();
                        }
                        JOptionPane.showMessageDialog(frame, "Login Successful!\nUsername: " + userName + "\nEmail: " + email);
                        break;
                    }
                }
                if (!found) {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
                fileScanner.close();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void signUp(JFrame frame) {
        JTextField userField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JPasswordField passField = new JPasswordField(20);
        Object[] message = {
                "Username:", userField,
                "Email:", emailField,
                "Password:", passField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Sign Up", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            userName = userField.getText();
            email = emailField.getText();
            password = new String(passField.getPassword());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("loginData.txt", true))) {
                writer.write(userName + "*" + email + "*" + password + "\n");
                JOptionPane.showMessageDialog(frame, "Sign Up Successful!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error writing to file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void forgot(JFrame frame) {
        JTextField userField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        Object[] message = {
                "Username:", userField,
                "Email:", emailField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Forgot Password", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            searchName = userField.getText();
            searchEmail = emailField.getText();

            try {
                File file = new File("loginData.txt");
                if (!file.exists()) {
                    throw new FileNotFoundException("File 'loginData.txt' not found");
                }
                Scanner fileScanner = new Scanner(file);
                boolean found = false;
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] data = line.split("\\*");
                    userName = data[0];
                    email = data[1];
                    password = data[2];
                    if (userName.equals(searchName) && email.equals(searchEmail)) {
                        found = true;
                        JOptionPane.showMessageDialog(frame, "Account found! Your password is: " + password);
                        break;
                    }
                }
                if (!found) {
                    JOptionPane.showMessageDialog(frame, "Account not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
                fileScanner.close();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showTeacherPanel() {
        TeacherPanel teacherPanel = new TeacherPanel(questions);
        teacherPanel.setVisible(true);
    }

    private void showStudentPanel() {
        StudentPanel studentPanel = new StudentPanel(questions);
        studentPanel.setVisible(true);
    }

    private List<Question> loadQuestionsFromFile() {
        List<Question> questions = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("questions.dat"))) {
            questions = (List<Question>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Handle exceptions
            e.printStackTrace();
        }
        return questions;
    }
}
