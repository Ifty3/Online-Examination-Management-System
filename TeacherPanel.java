import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherPanel extends JFrame {
    private List<Question> questionBank;

    public TeacherPanel(List<Question> questions) {
        this.questionBank = questions;
        displayTeacherPanel();
    }

    private void saveQuestionsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("questions.dat"))) {
            oos.writeObject(questionBank);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayTeacherPanel() {
        setTitle("Teacher Panel");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        JButton addQuestionButton = new JButton("Add Question");
        addQuestionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addQuestion();
            }
        });

        JButton deleteQuestionButton = new JButton("Delete Question");
        deleteQuestionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteQuestion();
            }
        });

        JButton modifyQuestionButton = new JButton("Modify Question");
        modifyQuestionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifyQuestion();
            }
        });

        panel.add(addQuestionButton);
        panel.add(deleteQuestionButton);
        panel.add(modifyQuestionButton);

        add(panel);
        setVisible(true);
    }

    private void addQuestion() {
        String questionText = JOptionPane.showInputDialog("Enter the question:");
        if (questionText == null || questionText.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Question text cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> choices = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            String choice = JOptionPane.showInputDialog("Enter choice " + (i + 1) + ":");
            if (choice == null || choice.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Choice text cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            choices.add(choice);
        }

        String correctAnswerIndexStr = JOptionPane.showInputDialog("Enter the index of the correct answer (1-4):");
        int correctAnswerIndex;
        try {
            correctAnswerIndex = Integer.parseInt(correctAnswerIndexStr) - 1;
            if (correctAnswerIndex < 0 || correctAnswerIndex >= 4) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid index. Please enter a number between 1 and 4.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Question newQuestion = new Question(questionText, choices, correctAnswerIndex);
        questionBank.add(newQuestion);
        saveQuestionsToFile();
    }

    private void deleteQuestion() {
        String[] options = new String[questionBank.size()];
        for (int i = 0; i < options.length; i++) {
            options[i] = String.valueOf(i + 1);
        }
        String choice = (String) JOptionPane.showInputDialog(null, "Select a question to delete:", "Delete Question", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice != null) {
            try {
                int index = Integer.parseInt(choice) - 1;
                if (index >= 0 && index < questionBank.size()) {
                    questionBank.remove(index);
                    saveQuestionsToFile();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid question index selected.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modifyQuestion() {
        String[] options = new String[questionBank.size()];
        for (int i = 0; i < options.length; i++) {
            options[i] = String.valueOf(i + 1);
        }

        String choice = (String) JOptionPane.showInputDialog(null, "Select a question to modify:", "Modify Question", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice != null) {
            try {
                int index = Integer.parseInt(choice) - 1;
                if (index >= 0 && index < questionBank.size()) {
                    Question question = questionBank.get(index);

                    String newQuestionText = JOptionPane.showInputDialog("Enter the new question text:", question.getQuestionText());
                    if (newQuestionText == null || newQuestionText.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Question text cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    List<String> newChoices = new ArrayList<>();
                    for (int i = 0; i < 4; i++) {
                        String choiceText = JOptionPane.showInputDialog("Enter choice " + (i + 1) + ":", question.getChoices().get(i));
                        if (choiceText == null || choiceText.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Choice text cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        newChoices.add(choiceText);
                    }

                    String newCorrectAnswerIndexStr = JOptionPane.showInputDialog("Enter the index of the correct answer (1-4):", question.getCorrectAnswerIndex() + 1);
                    int newCorrectAnswerIndex;
                    try {
                        newCorrectAnswerIndex = Integer.parseInt(newCorrectAnswerIndexStr) - 1;
                        if (newCorrectAnswerIndex < 0 || newCorrectAnswerIndex >= 4) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid index. Please enter a number between 1 and 4.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Question modifiedQuestion = new Question(newQuestionText, newChoices, newCorrectAnswerIndex);
                    questionBank.set(index, modifiedQuestion);
                    saveQuestionsToFile();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid question index selected.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
