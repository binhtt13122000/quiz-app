package binhtt.dtos;

import java.io.Serializable;
import java.util.List;

public class QuestionDTO implements Serializable {
    private String id;
    private String question;
    private boolean status;
    private String subId;
    private List<AnswerOfQuestionDTO> answerOfQuestionDTOS;
    private String selectedAnswer;

    public QuestionDTO(String id, String question, boolean status, String subId, List<AnswerOfQuestionDTO> answerOfQuestionDTOS) {
        this.id = id;
        this.question = question;
        this.status = status;
        this.subId = subId;
        this.answerOfQuestionDTOS = answerOfQuestionDTOS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public List<AnswerOfQuestionDTO> getAnswerOfQuestionDTOS() {
        return answerOfQuestionDTOS;
    }

    public void setAnswerOfQuestionDTOS(List<AnswerOfQuestionDTO> answerOfQuestionDTOS) {
        this.answerOfQuestionDTOS = answerOfQuestionDTOS;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}
