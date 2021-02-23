package binhtt.daos;

import binhtt.constants.Constants;
import binhtt.db.MyConnection;
import binhtt.dtos.AnswerOfQuestionDTO;
import binhtt.dtos.QuestionDTO;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionDAO implements Serializable {
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

    public boolean create(QuestionDTO questionDTO) throws Exception{
        boolean isSuccess;
        try {
            String sql = "insert into TblQuestion(id, question_content, status, subId) values (?,?,1,?)";
            String sqlAnswer = "insert into tblAnswerOfQuestion(id, answer_content, isCorrect, questionId) values (?, ?, ?, ?)";
            connection = MyConnection.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,questionDTO.getId());
            preparedStatement.setString(2, questionDTO.getQuestion());
            preparedStatement.setString(3, questionDTO.getSubId());
            int numOfQuestion = preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sqlAnswer);
            int numOfAnswer = 0;
            for (AnswerOfQuestionDTO answer: questionDTO.getAnswerOfQuestionDTOS()) {
                preparedStatement = connection.prepareStatement(sqlAnswer);
                System.out.println(answer.getId());
                preparedStatement.setString(1, answer.getId());
                preparedStatement.setString(2, answer.getContent());
                preparedStatement.setBoolean(3, answer.isCorrect());
                preparedStatement.setString(4, answer.getQuestionId());
                numOfAnswer += preparedStatement.executeUpdate();
            }
            if(numOfQuestion + numOfAnswer == 1 + questionDTO.getAnswerOfQuestionDTOS().size()){
                isSuccess = true;
                connection.commit();
            } else {
                isSuccess = false;
                connection.rollback();
            }
        } finally {
            closeConnection();
        }
        return isSuccess;
    }

    public boolean update(QuestionDTO questionDTO) throws Exception {
        boolean isSuccess;
        try {
            String sql = "update TblQuestion set question_content = ?, subId = ? where id = ?";
            String answerSql = "update tblAnswerOfQuestion set answer_content = ?, isCorrect = ? where id = ?";
            connection = MyConnection.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, questionDTO.getQuestion());
            preparedStatement.setString(2, questionDTO.getSubId());
            preparedStatement.setString(3, questionDTO.getId());
            int numOfQuestion = preparedStatement.executeUpdate();
            int numOfAnswer = 0;
            for (AnswerOfQuestionDTO answer: questionDTO.getAnswerOfQuestionDTOS()) {
                preparedStatement = connection.prepareStatement(answerSql);
                preparedStatement.setString(1, answer.getContent());
                preparedStatement.setBoolean(2, answer.isCorrect());
                preparedStatement.setString(3, answer.getId());
                numOfAnswer += preparedStatement.executeUpdate();
            }
            if(numOfQuestion + numOfAnswer == 1 + questionDTO.getAnswerOfQuestionDTOS().size()){
                isSuccess = true;
                connection.commit();
            } else {
                isSuccess = false;
                connection.rollback();
            }
        } finally {
            closeConnection();
        }
        return isSuccess;
    }

    public boolean delete(String id) throws Exception {
        boolean isSuccess;
        try {
            String sql = "update TblQuestion set status = 0 where id = ?";
            connection = MyConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            isSuccess = preparedStatement.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return isSuccess;
    }

    public List<QuestionDTO> getQuestions(int page, String name, boolean status,String subjectId) throws Exception{
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        Connection ansConnection = null;
        PreparedStatement ansPs = null;
        ResultSet ansRs = null;
        String questionId = null;
        try {
            String sql = "select id, question_content, subId, status from TblQuestion where question_content like ? and (? = 'all' or subId = ?) and (? = 1 or status = 0) order by question_content desc offset ?*(? - 1) rows fetch next ? rows only";
            String sqlAnswer = "select id, answer_content, isCorrect, questionId from tblAnswerOfQuestion where questionId = ?";
            connection = MyConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + name + "%");
            preparedStatement.setString(2, subjectId);
            preparedStatement.setString(3, subjectId);
            preparedStatement.setInt(4, status ? 1 : 0);
            preparedStatement.setInt(5, Constants.PAGE_SIZE);
            preparedStatement.setInt(6, page);
            preparedStatement.setInt(7, Constants.PAGE_SIZE);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                List<AnswerOfQuestionDTO> answers = new ArrayList<>();
                try {
                    questionId = resultSet.getString("id");
                    ansConnection = MyConnection.getConnection();
                    ansPs = ansConnection.prepareStatement(sqlAnswer);
                    ansPs.setString(1, questionId);
                    ansRs = ansPs.executeQuery();
                    while (ansRs.next()){
                        answers.add(new AnswerOfQuestionDTO(ansRs.getString("id"), ansRs.getString("answer_content"), ansRs.getBoolean("isCorrect"), questionId));
                    }
                } finally {
                    if(ansRs != null){
                        ansRs.close();
                    }
                    if(ansPs != null){
                        ansPs.close();
                    }
                    if(ansConnection != null){
                        ansConnection.close();
                    }
                }
                questionDTOs.add(new QuestionDTO(questionId, resultSet.getString("question_content"), resultSet.getBoolean("status"), resultSet.getString("subId"), answers));
            }
        } finally {
            closeConnection();
        }
        return questionDTOs;
    }

    public int getTotalOfQuestions(String name, boolean status,String subjectId) throws Exception{
        int count = -1;
        try {
            String sql = "select count(id) as counter from TblQuestion where question_content like ? and (? = 'all' or subId = ?) and (? = 1 or status = 0)";
            connection = MyConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + name + "%");
            preparedStatement.setString(2, subjectId);
            preparedStatement.setString(3, subjectId);
            preparedStatement.setInt(4, status ? 1 : 0);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                count = resultSet.getInt("counter");
            }
        } finally {
            closeConnection();
        }
        return count;
    }


    public List<QuestionDTO> getQuestionsForTest(int questionPerQuiz, String subjectId) throws Exception{
        Connection ansConnection = null;
        PreparedStatement ansPs = null;
        ResultSet ansRs = null;
        String questionId = null;
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        try {
            String sql = "select id, question_content, subId, status from TblQuestion where subID = ? and status = 1 order by newid()";
            String sqlAnswer = "select id, answer_content, isCorrect, questionId from tblAnswerOfQuestion where questionId = ?";
            connection = MyConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, subjectId);
            preparedStatement.setMaxRows(questionPerQuiz);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                List<AnswerOfQuestionDTO> answers = new ArrayList<>();
                try {
                    questionId = resultSet.getString("id");
                    ansConnection = MyConnection.getConnection();
                    ansPs = ansConnection.prepareStatement(sqlAnswer);
                    ansPs.setString(1, questionId);
                    ansRs = ansPs.executeQuery();
                    while (ansRs.next()){
                        answers.add(new AnswerOfQuestionDTO(ansRs.getString("id"), ansRs.getString("answer_content"), ansRs.getBoolean("isCorrect"), questionId));
                    }
                } finally {
                    if(ansRs != null){
                        ansRs.close();
                    }
                    if(ansPs != null){
                        ansPs.close();
                    }
                    if(ansConnection != null){
                        ansConnection.close();
                    }
                }
                questionDTOs.add(new QuestionDTO(questionId, resultSet.getString("question_content"), resultSet.getBoolean("status"), resultSet.getString("subId"), answers));
            }
        } finally {
            closeConnection();
        }
        return questionDTOs;
    }


    public String generateId(String subjectId) throws Exception {
        String newId;
        try {
            String sql = "select count(id) as count from TblQuestion where subId = ?";
            connection = MyConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, subjectId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int index = resultSet.getInt("count") + 1;
                newId = subjectId + "_Question_" + index;
            } else {
                newId = subjectId + "_Question_1";
            }
        } finally {
            closeConnection();
        }
        return newId;
    }
}
