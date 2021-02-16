package binhtt.daos;

import binhtt.db.MyConnection;
import binhtt.dtos.UserDTO;
import binhtt.utils.password.PasswordUtils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO implements Serializable {
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

    //login
    public UserDTO login(String email, String password) throws Exception {
        UserDTO user = null;
        try {
            String query = "select email, name, status, roleId from TblUser where email = ? and password = ?;";
            connection = MyConnection.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, PasswordUtils.encode(password));
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user = new UserDTO(resultSet.getString("email"), resultSet.getString("name"),resultSet.getBoolean("status"), resultSet.getInt("roleId"));
            }
        } finally {
            closeConnection();
        }
        return user;
    }

    //register
    public boolean register(String email, String name, String password) throws Exception {
        boolean isSuccess;
        try {
            String query = "insert into TblUser(email, password, roleId, status, name) values (?, ?, 1, 1, ?)";
            connection = MyConnection.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, PasswordUtils.encode(password));
            preparedStatement.setString(3, name);
            isSuccess = preparedStatement.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return isSuccess;
    }

}
