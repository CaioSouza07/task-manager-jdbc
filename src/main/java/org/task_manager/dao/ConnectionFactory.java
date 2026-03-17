package org.task_manager.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static ConnectionFactory instance;

    public ConnectionFactory() {
    }

    public static ConnectionFactory getInstance() {
        if(instance == null) instance = new ConnectionFactory();
        return instance;
    }

    public Connection get() throws SQLException {

        String url = "jdbc:mysql://localhost:5432/task_manager";
        String user = "postgres";
        String password = "postgres";

        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

}
