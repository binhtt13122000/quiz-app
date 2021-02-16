package binhtt.dtos;

import java.io.Serializable;

public class AnswerDTO implements Serializable {
    private String id;
    private String quizId;
    private String questionId;
    private boolean isCorrect;
    private int choice;

    public AnswerDTO(String id, String quizId, String questionId, boolean isCorrect, int choice) {
        this.id = id;
        this.quizId = quizId;
        this.questionId = questionId;
        this.isCorrect = isCorrect;
        this.choice = choice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }
}
