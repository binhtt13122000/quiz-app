package binhtt.daos;

import binhtt.db.MyConnection;
import binhtt.dtos.SubjectDTO;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO implements Serializable {
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

    public List<SubjectDTO> getSubjects() throws Exception {
        List<SubjectDTO> subjectDTOS = new ArrayList<>();
        String id = null;
        String name = null;
        int timeToTakeQuiz = -1;
        int questionPerQuiz = -1;
        float pointPerQuestion = -1f;
        try {
            String sql = "select id, name, timeToTakeQuiz, totalOfQuestionsPerQuiz, pointPerQuestion from TblSubject";
            connection = MyConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                id = resultSet.getString("id");
                name = resultSet.getString("name");
                timeToTakeQuiz = resultSet.getInt("timeToTakeQuiz");
                questionPerQuiz = resultSet.getInt("totalOfQuestionsPerQuiz");
                pointPerQuestion = resultSet.getFloat("pointPerQuestion");
                subjectDTOS.add(new SubjectDTO(id, name, timeToTakeQuiz, questionPerQuiz, pointPerQuestion));
            }
        } finally {
            closeConnection();
        }
        return subjectDTOS;
    }
}
