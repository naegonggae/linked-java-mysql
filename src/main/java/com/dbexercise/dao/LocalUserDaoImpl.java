package com.dbexercise.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

// local db 를 사용할때 구현체
// local 은 해커들이 못털어간다고함 대신 비슷한 비밀번호 주의
public class LocalUserDaoImpl extends UserDaoAbstract{
    @Override
    public Connection makeConnection() throws SQLException {
        Map<String, String> env = System.getenv();
        // db 접속
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://ec2-15-165-71-224.ap-northeast-2.compute.amazonaws.com/likelion-db",
                "root", "비밀번호");
        return connection;
    }
}
