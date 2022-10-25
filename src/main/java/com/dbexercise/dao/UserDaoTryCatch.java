package com.dbexercise.dao;

import com.dbexercise.domain.User;

import java.sql.*;
import java.util.Map;

public class UserDaoTryCatch {
    public void add() {
        Map<String, String> env = System.getenv();
        try {
            // db 접속
            Connection connection = DriverManager.getConnection(
                    env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD")
            );
            // query 문 작성
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO users(id, name, password) VALUES(?,?,?)");
            preparedStatement.setString(1,"3");
            preparedStatement.setString(2, "ya");
            preparedStatement.setString(3, "4321");
            // query 문 실행
            preparedStatement.executeUpdate(); // command + enter 역할
            preparedStatement.close();
            connection.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public User findById(String id) {
        Map<String, String> env = System.getenv();
        Connection c;
        try {
            // DB접속 (ex sql workbeanch실행)
            c = DriverManager.getConnection(
                    env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD"));

            // Query문 작성
            PreparedStatement preparedStatement = c.prepareStatement("SELECT * FROM users WHERE id = ?");
            preparedStatement.setString(1, id);

            // Query문 실행
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            User user = new User(rs.getString("id"), rs.getString("name"),
                    rs.getString("password"));

            rs.close();
            preparedStatement.close();
            c.close();

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        UserDaoTryCatch userDaoTryCatch = new UserDaoTryCatch();
        //userDaoTryCatch.add();

        User user = userDaoTryCatch.findById("3");
        System.out.println(user.getName());
    }
}
