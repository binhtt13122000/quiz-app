/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binhtt.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;

/**
 *
 * @author binht
 */
public class MyConnection implements Serializable {
    public static Connection getConnection() throws Exception {
        Context context = new InitialContext();
        Context contextEnv = (Context) context.lookup("java:comp/env");
        DataSource dataSource = (DataSource) contextEnv.lookup("DBConnection");
        return dataSource.getConnection();
    }
}
