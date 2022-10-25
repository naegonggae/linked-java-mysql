package com.dbexercise.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LocalConnectionMaker implements ConnectionMaker{
    @Override
    public Connection makeConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://ec2-15-165-71-224.ap-northeast-2.compute.amazonaws.com/likelion-db",
                "root",
                "비밀번호");
        return connection;
    }
}
