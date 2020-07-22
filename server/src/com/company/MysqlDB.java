package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MysqlDB {
    private static final String DBMS = "mysql";
    private static final String SERVER = "sql2.freemysqlhosting.net";
    private static final String NAME = "sql2354288";
    private static final String USERNAME = "sql2354288";
    private static final String PASSWORD = "pJ1*rK5%";
    private static final String PORT = "3306";

    //Establishing a connection with the database
    public Connection getConnection() throws SQLException {

        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.USERNAME);
        connectionProps.put("password", this.PASSWORD);

        conn = DriverManager.getConnection(
                "jdbc:" + this.DBMS + "://" +
                        this.SERVER +
                        ":" + this.PORT + "/" + this.NAME,
                connectionProps);
        return conn;
    }
}
