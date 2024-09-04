import java.io.Serializable;

/*
 imports the
Serializable interface from the java.io package.
This allows objects of the Question class to be converted into a format that can be stored or transmitted.
 */
import java.util.List;

public class Question implements Serializable
/*
 defines a public class named
Question that implements the Serializable interface.
This means the class can be serialized and any object of this class can be stored or transmitted.
 */

{
    private String questionText;
    private List<String> choices;
    private int correctAnswerIndex;
    //encaptulation use here
    public Question(String questionText, List<String> choices, int correctAnswerIndex) {
        this.questionText = questionText;
        this.choices = choices;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getChoices() {
        return choices;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public String getCorrectAnswer() {
        return choices.get(correctAnswerIndex);
    }

    public boolean isCorrectAnswer(int index) {
        return index == correctAnswerIndex;
    }
}
