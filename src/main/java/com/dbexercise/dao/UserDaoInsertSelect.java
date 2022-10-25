package com.dbexercise.dao;

import com.dbexercise.domain.User;

import java.sql.*;
import java.util.Map;

public class UserDaoInsertSelect {
    public void add() throws SQLException, ClassNotFoundException {
        // 해킹안당하려고 환경변수 env에 있는 정보를 끌고오는 로직을 짬
        Map<String, String> env = System.getenv();
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver");
        // db 연결
        Connection connection = DriverManager.getConnection(dbHost, dbUser, dbPassword);
        // 쿼리문 작성
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO users(id, name, password) VALUES(?,?,?)");
        preparedStatement.setString(1,"1");
        preparedStatement.setString(2, "david");
        preparedStatement.setString(3, "4321");

        int status = preparedStatement.executeUpdate(); // command + enter 역할
        System.out.println(status);
        preparedStatement.close();
        connection.close();
    }

    // Select 랑 똑같네
    public User get(String id) throws ClassNotFoundException, SQLException {
        Map<String, String> env = System.getenv();
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver");
        // db 연결
        Connection connection = DriverManager.getConnection(dbHost, dbUser, dbPassword);
        // 쿼리문 작성
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, name, password FROM users WHERE id = ?");
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
        UserDaoInsertSelect userDaoInsertSelect = new UserDaoInsertSelect();
        userDaoInsertSelect.add();

        User user = userDaoInsertSelect.get("1");
        System.out.println(user.getName());
    }
}
