package binhtt.dtos;

import java.io.Serializable;

public class AnswerOfQuestionDTO implements Serializable {
    private String id;
    private String content;
    private boolean isCorrect;
    private String questionId;

    public AnswerOfQuestionDTO(String id, String content, boolean isCorrect, String questionId) {
        this.id = id;
        this.content = content;
        this.isCorrect = isCorrect;
        this.questionId = questionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
