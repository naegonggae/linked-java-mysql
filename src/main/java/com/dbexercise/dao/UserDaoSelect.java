package com.dbexercise.dao;

import com.dbexercise.domain.User;

import java.sql.*;
import java.util.Map;

public class UserDaoSelect {
    public User selct(String id) throws SQLException, ClassNotFoundException {
        // 해킹안당하려고 환경변수 env에 있는 정보를 끌고오는 로직을 짬
        Map<String, String> env = System.getenv();
        // db 연결
        Connection connection = DriverManager.getConnection(
                env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD")
        );
        // 쿼리문 작성
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM users where id = ?");
        preparedStatement.setString(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
        rs.close();
        preparedStatement.close();
        connection.close();

        return user;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDaoSelect userDaoSelect = new UserDaoSelect();
        User user = userDaoSelect.selct("0");
        System.out.println(user.getName());
    }
}
