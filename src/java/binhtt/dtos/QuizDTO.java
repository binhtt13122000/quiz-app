package binhtt.dtos;

import java.io.Serializable;
import java.sql.Timestamp;

public class QuizDTO implements Serializable {
    private String id;
    private String userId;
    private String subjectId;
    private Timestamp start;
    private Timestamp end;
    private int point;
    private boolean isSubmit;

    public boolean isSubmit() {
        return isSubmit;
    }

    public void setSubmit(boolean submit) {
        isSubmit = submit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public QuizDTO(String id, String userId, String subjectId, Timestamp start, Timestamp end, int point) {
        this.id = id;
        this.userId = userId;
        this.subjectId = subjectId;
        this.start = start;
        this.end = end;
        this.point = point;
    }

    public QuizDTO(String id, String userId, String subjectId, Timestamp start, Timestamp end, int point, boolean isSubmit) {
        this.id = id;
        this.userId = userId;
        this.subjectId = subjectId;
        this.start = start;
        this.end = end;
        this.point = point;
        this.isSubmit = isSubmit;
    }
}
