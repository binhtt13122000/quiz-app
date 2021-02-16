package binhtt.dtos;

import java.io.Serializable;

public class SubjectDTO implements Serializable {
    private String id;
    private String name;
    private int timeToTakeQuiz;
    private int totalOfQuestionsPerQuiz;
    private float pointPerQuestion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeToTakeQuiz() {
        return timeToTakeQuiz;
    }

    public void setTimeToTakeQuiz(int timeToTakeQuiz) {
        this.timeToTakeQuiz = timeToTakeQuiz;
    }

    public int getTotalOfQuestionsPerQuiz() {
        return totalOfQuestionsPerQuiz;
    }

    public void setTotalOfQuestionsPerQuiz(int totalOfQuestionsPerQuiz) {
        this.totalOfQuestionsPerQuiz = totalOfQuestionsPerQuiz;
    }

    public float getPointPerQuestion() {
        return pointPerQuestion;
    }

    public void setPointPerQuestion(float pointPerQuestion) {
        this.pointPerQuestion = pointPerQuestion;
    }

    public SubjectDTO(String id, String name, int timeToTakeQuiz, int totalOfQuestionsPerQuiz, float pointPerQuestion) {
        this.id = id;
        this.name = name;
        this.timeToTakeQuiz = timeToTakeQuiz;
        this.totalOfQuestionsPerQuiz = totalOfQuestionsPerQuiz;
        this.pointPerQuestion = pointPerQuestion;
    }
}
