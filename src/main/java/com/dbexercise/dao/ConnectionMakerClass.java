package com.dbexercise.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

// makeConnection 을 아예 클래스로 분리함
// 클래스로 분리했을때 문제점 : 구체적인 이름을 알고있어 확장이 어렵다. UserDao 와 특정클래스가 강하게 결합됨
public class ConnectionMakerClass {
    // private 안되고 public 으로 수정. 추상클래스도 마찬가지
    public Connection makeConnection() throws SQLException {
        Map<String, String> env = System.getenv();
        // db 접속
        Connection connection = DriverManager.getConnection(
                env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD"));
        return connection;
    }
}
