package com.dbexercise.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// add 메소드로 db에 insert 하기
// Primary 이기 때문에 두번은 안올라가
// 해킹당하는 insert
public class UserDaoEX { // data access object, db에 접근하는 객체
    public void add() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://ec2-15-165-71-224.ap-northeast-2.compute.amazonaws.com/likelion-db",
                "root", "비밀번호"
        );
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO users(id, name, password) value(?,?,?)"
        );
        preparedStatement.setString(1, "5");
        preparedStatement.setString(2, "sanghun");
        preparedStatement.setString(3, "1223");

        int status = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDaoEX userDao = new UserDaoEX();
        userDao.add();
    }
}
