import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentPanel extends JFrame {
    private List<Question> questions;
    private JLabel questionLabel;
    private JRadioButton[] choiceButtons;
    private JButton submitButton;
    private int currentQuestionIndex = 0;
    private ButtonGroup buttonGroup;

    public StudentPanel(List<Question> questions) {
        this.questions = questions;
        this.setTitle("Student Panel");
        this.setSize(500, 300);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.initComponents();
        this.displayQuestion(currentQuestionIndex);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        questionLabel = new JLabel();
        panel.add(questionLabel);

        choiceButtons = new JRadioButton[4];
        buttonGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            choiceButtons[i] = new JRadioButton();
            buttonGroup.add(choiceButtons[i]);
            panel.add(choiceButtons[i]);
        }

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitAnswer();
            }
        });
        panel.add(submitButton);

        this.add(panel);
    }

    private void displayQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            Question question = questions.get(index);
            questionLabel.setText(question.getQuestionText());
            List<String> choices = question.getChoices();
            for (int i = 0; i < choices.size(); i++) {
                choiceButtons[i].setText(choices.get(i));
            }
        }
    }

    private void submitAnswer() {
        for (int i = 0; i < choiceButtons.length; i++) {
            if (choiceButtons[i].isSelected()) {
                String selectedChoice = choiceButtons[i].getText();
                if (questions.get(currentQuestionIndex).isCorrectAnswer(i)) {
                    JOptionPane.showMessageDialog(this, "Correct!");
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect. Correct answer is: " + questions.get(currentQuestionIndex).getCorrectAnswer());
                }
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    displayQuestion(currentQuestionIndex);
                    buttonGroup.clearSelection();
                } else {
                    JOptionPane.showMessageDialog(this, "End of exam!");
                    this.dispose();
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Please select an answer!");
    }
}
