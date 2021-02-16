package binhtt.daos;

import binhtt.constants.Constants;
import binhtt.db.MyConnection;
import binhtt.dtos.QuestionDTO;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
            String sql = "insert into TblQuestion(id, question_content, answerA, answerB, answerC, answerD, correctAnswer, status, subId) values (?,?,?,?,?,?,?,1,?)";
            connection = MyConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,questionDTO.getId());
            preparedStatement.setString(2, questionDTO.getQuestion());
            preparedStatement.setString(3, questionDTO.getAnswerA());
            preparedStatement.setString(4, questionDTO.getAnswerB());
            preparedStatement.setString(5, questionDTO.getAnswerC());
            preparedStatement.setString(6, questionDTO.getAnswerD());
            preparedStatement.setInt(7, questionDTO.getCorrectAnswer());
            preparedStatement.setString(8, questionDTO.getSubId());
            isSuccess = preparedStatement.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return isSuccess;
    }

    public boolean update(QuestionDTO questionDTO) throws Exception {
        boolean isSuccess;
        try {
            String sql = "update TblQuestion set question_content = ?, answerA = ?, answerB = ?, answerC = ?, answerD = ?, correctAnswer = ?, subId = ? where id = ?";
            connection = MyConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, questionDTO.getQuestion());
            preparedStatement.setString(2, questionDTO.getAnswerA());
            preparedStatement.setString(3, questionDTO.getAnswerB());
            preparedStatement.setString(4, questionDTO.getAnswerC());
            preparedStatement.setString(5, questionDTO.getAnswerD());
            preparedStatement.setInt(6, questionDTO.getCorrectAnswer());
            preparedStatement.setString(7, questionDTO.getSubId());
            preparedStatement.setString(8, questionDTO.getId());
            isSuccess = preparedStatement.executeUpdate() > 0;
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
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        try {
            String sql = "select id, question_content, answerA, answerB, answerC, answerD, subId, status, correctAnswer from TblQuestion where question_content like ? and (? = 'all' or subId = ?) and (? = 1 or status = 0) order by question_content desc offset ?*(? - 1) rows fetch next ? rows only";
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
                questionDTOS.add(new QuestionDTO(resultSet.getString("id"), resultSet.getString("question_content"), resultSet.getString("answerA"), resultSet.getString("answerB"), resultSet.getString("answerC"), resultSet.getString("answerD"), resultSet.getInt("correctAnswer"), resultSet.getBoolean("status"), resultSet.getString("subId")));
            }
        } finally {
            closeConnection();
        }
        return questionDTOS;
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
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        try {
            String sql = "select id, question_content, answerA, answerB, answerC, answerD, subId, status, correctAnswer from TblQuestion where subId = ? and status = 1 order by newid()";
            connection = MyConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, subjectId);
            preparedStatement.setMaxRows(questionPerQuiz);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                questionDTOS.add(new QuestionDTO(resultSet.getString("id"), resultSet.getString("question_content"), resultSet.getString("answerA"), resultSet.getString("answerB"), resultSet.getString("answerC"), resultSet.getString("answerD"), resultSet.getInt("correctAnswer"), resultSet.getBoolean("status"), resultSet.getString("subId")));
            }
        } finally {
            closeConnection();
        }
        return questionDTOS;
    }


    public String generateId(String subjectId) throws Exception {
        String newId;
        try {
            String sql = "select id from TblQuestion where subId = ? order by id desc";
            connection = MyConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, subjectId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String id = resultSet.getString("id");
                int index = Integer.parseInt(id.split("_")[2]) + 1;
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
