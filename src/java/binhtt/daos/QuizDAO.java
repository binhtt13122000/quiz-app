package binhtt.daos;

import binhtt.db.MyConnection;
import binhtt.dtos.AnswerOfQuestionDTO;
import binhtt.dtos.QuestionDTO;
import binhtt.dtos.QuizDTO;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuizDAO implements Serializable {
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private void closeConnection() throws Exception {
        if(resultSet != null){
            resultSet.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        if(connection != null){
            connection.close();
        }
    }

    public List<QuizDTO> getQuiz(String subId, String email) throws Exception{
        List<QuizDTO> quizDTOS = new ArrayList<>();
        try {
            String query = "select id, userId, subjectId, startTime, endTime, point  from TblQuiz where subjectId = ? and userId = ?";
            connection = MyConnection.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, subId);
            preparedStatement.setString(2, email);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                quizDTOS.add(new QuizDTO(resultSet.getString("id"), resultSet.getString("userId"), resultSet.getString("subjectId"), resultSet.getTimestamp("startTime"), resultSet.getTimestamp("endTime"), resultSet.getInt("point")));
            }
        } finally {
            closeConnection();
        }
        return quizDTOS;
    }

    public boolean createQuiz(String quizId, String email, int time, String subId, List<QuestionDTO> questionDTOS) throws Exception{
        try {
            String queryQuiz = "insert into TblQuiz(id, userId, subjectId, startTime, endTime, point) values (?,?,?,?,?,?)";
            String queryAnswer = "insert into TblAnswer(id, quizId, questionId, isCorrect, choice) VALUES (?,?,?,?,?)";
            connection = MyConnection.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(queryQuiz);
            preparedStatement.setString(1, quizId);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, subId);
            preparedStatement.setTimestamp(4, new Timestamp(new Date().getTime()));
            preparedStatement.setTimestamp(5, new Timestamp(new Date().getTime() + time * 60 * 1000));
            preparedStatement.setInt(6, 0);
            int checkQuiz = preparedStatement.executeUpdate();
            int count = 1;
            int checkAnswer = 0;
            for (QuestionDTO questionDTO: questionDTOS) {
                preparedStatement = connection.prepareStatement(queryAnswer);
                preparedStatement.setString(1, quizId + "-" + count++);
                preparedStatement.setString(2, quizId);
                preparedStatement.setString(3, questionDTO.getId());
                preparedStatement.setBoolean(4, false);
                String idOfNotChosen = "";
                for (AnswerOfQuestionDTO answer: questionDTO.getAnswerOfQuestionDTOS()) {
                    int index = Integer.parseInt(answer.getId().split("-")[answer.getId().split("-").length - 1]);
                    if(index == 0){
                        idOfNotChosen = answer.getId();
                        break;
                    }
                }
                preparedStatement.setString(5, idOfNotChosen);
                checkAnswer += preparedStatement.executeUpdate();
            }
            if(checkQuiz + checkAnswer == questionDTOS.size() + 1){
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } finally {
            closeConnection();
        }
    }

    public int getPoint(String[] answers, List<QuestionDTO> questionDTOS){
        int index = 0;
        int point = 0;
        for (String answer: answers) {
            List<AnswerOfQuestionDTO> answerOfQuestionDTOS = questionDTOS.get(index++).getAnswerOfQuestionDTOS();
            String rightAnswerId = "";
            for (AnswerOfQuestionDTO ans: answerOfQuestionDTOS) {
                if(ans.isCorrect()){
                    rightAnswerId = ans.getId();
                    break;
                }
            }
            int indexRightAnswer = Integer.parseInt(rightAnswerId.split("-")[rightAnswerId.split("-").length - 1]);
            if(indexRightAnswer == Integer.parseInt(answer)){
                point += (100 / questionDTOS.size());
            }
        }
        return point;
    }

    public boolean submit(String quizId, String[] answers, List<QuestionDTO> questionDTOS) throws Exception {
        try {
            String sqlQuiz = "update TblQuiz set point = ? where id = ?";
            connection = MyConnection.getConnection();
            preparedStatement = connection.prepareStatement(sqlQuiz);
            preparedStatement.setInt(1, getPoint(answers, questionDTOS));
            preparedStatement.setString(2, quizId);
            return preparedStatement.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
    }
}
