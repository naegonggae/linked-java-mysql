package com.dbexercise.dao;

import com.dbexercise.domain.User;

import java.sql.*;
import java.util.Map;

// 추상클래스 Abstract 메소드 한개이상을 가지고 있음, 반드시 구현체 구현해줘야함
// 양쪽 db 에서 모두 사용하고 싶을때
// 추상화는 makeConnection, 구현체는 UserDao 의 add(), select()
// 구현하는데 있어 UserDaoAbstract 에 의존을 심하게 받음. 각각의 구현체에서의 변화는 받아들이지 못함 => 사용을 지양한다.
public abstract class UserDaoAbstract {
    public abstract Connection makeConnection() throws SQLException;

    public void add(User user) {
        Map<String, String> env = System.getenv();
        try {
            // db 접속
            Connection connection = makeConnection();
            /* connection 분리
            Connection connection = DriverManager.getConnection(
                    env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD")
            */
            // query 문 작성
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO users(id, name, password) VALUES(?,?,?)");
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPassword());
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
        Connection connection;
        try {
            // DB접속 (ex sql workbeanch실행)
            connection = makeConnection();
            /* connection 분리
            Connection connection = DriverManager.getConnection(
                    env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD")
            */
            // Query문 작성
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            preparedStatement.setString(1, id);

            // Query문 실행
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            User user = new User(rs.getString("id"), rs.getString("name"),
                    rs.getString("password"));

            rs.close();
            preparedStatement.close();
            connection.close();

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        userDao.add(new User("5","bibi", "1122"));
    }
}
