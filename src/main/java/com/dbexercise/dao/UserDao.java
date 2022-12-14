package com.dbexercise.dao;

import ch.qos.logback.core.joran.conditional.IfAction;
import com.dbexercise.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;
import java.util.EmptyStackException;
import java.util.Map;

public class UserDao {
    // 하드코딩 되어있는걸 밖으로 꺼냄
    // connection 분리하기 ;계속 반복되니까 관심사의 분리시키는 것임 / 메소드로 분리, 클래스로 분리, 인터페이스로 분리 중 메소드로 분리
    //private ConnectionMakerClass connectionMakerClass = new ConnectionMakerClass(); // 생성자 생성하기 전에 코드
    // 스프링은 그냥 패턴화, 자동화 시켜주는 느낌
    private ConnectionMaker connectionMaker; // 인터페이스 구현
    //private ConnectionMakerClass connectionMakerClass; // 생성자 생성하고 코드 // 인터페이스 구현하고 없어진 코드
    /* ConnectionMakerClass 클래스로 분리해서 안씀
    private Connection makeConnection() throws SQLException {

        Map<String, String> env = System.getenv();
        // db 접속
        Connection connection = DriverManager.getConnection(
                env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD"));
        return connection;
    }
    */
    /*
    public UserDao(ConnectionMakerClass connectionMakerClass) { // 이거는 아래 생성자랑 뭐가 다를까?
        this.connectionMakerClass = connectionMakerClass;
    }
     */

    public UserDao() {
        //this.connectionMakerClass = new ConnectionMakerClass(); // 인터페이스 구현하고 변경됨
        this.connectionMaker= new AwsConnectionMaker();
    }

    public UserDao(ConnectionMaker connectionMaker) { // 메소드 오버로딩 ; 기본값은 위의 생성자이지만 이걸로 다른거 적용시킬수있어서 구현함
        this.connectionMaker = connectionMaker;
    }
    public void jdbcContextWithStatementStrategy(StatementStrategy statementStrategy) throws SQLException {
        Connection connection  = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionMaker.makeConnection();
            preparedStatement = statementStrategy.makePreparedStatement(connection);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally { // error 가 나도 실행되는 부분
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }
    public void deleteAll() throws SQLException {
        //Connection connection  = null;
        //PreparedStatement preparedStatement = null;

        jdbcContextWithStatementStrategy(new DeleteAllStrategty());
        /*
        try {
            connection = connectionMaker.makeConnection();
            preparedStatement = new DeleteAllStrategty().makePreparedStatement(connection); // deleteAll 수정
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally { // error 가 나도 실행되는 부분
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
         */
    }

    public int getCount() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        int count = 0;
        try {
            connection = connectionMaker.makeConnection();
            preparedStatement = connection.prepareStatement("Select count(*) from `likelion-db`.users");
            rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void add(User user) throws SQLException {
        AddStrategy addStrategy = new AddStrategy(user);
        jdbcContextWithStatementStrategy(addStrategy);
        /*
        //Map<String, String> env = System.getenv();
        try {
            // db 접속
            //Connection connection = connectionMakerClass.makeConnection(); // 인터페이스 구현하고 없어짐
            Connection connection = connectionMaker.makeConnection();
            // connection = makeConnection(); ConnectionMakerClass 로 분리하기 전 코드
            /* connection 분리
            Connection connection = DriverManager.getConnection(
                    env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD")
            */
            // query 문 작성
        /*
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
        */
    }

    public User findById(String id) {
        Map<String, String> env = System.getenv();
        Connection connection;
        try {
            // DB접속 (ex sql workbeanch실행)
            //connection = connectionMakerClass.makeConnection(); // 인터페이스 구현하고 없어짐
            connection = connectionMaker.makeConnection();
            // connection = makeConnection(); 클래스 분리 전 코드

            /* connection 분리
            Connection connection = DriverManager.getConnection(
                    env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD")
            */
            // Query문 작성
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            preparedStatement.setString(1, id);

            // Query문 실행
            ResultSet rs = preparedStatement.executeQuery();
            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("id"), rs.getString("name"),
                        rs.getString("password"));
            }

                rs.close();
                preparedStatement.close();
                connection.close();

                if (user == null) throw new EmptyResultDataAccessException(1);
                    return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        //userDao.add(new User("5","bibi", "1122"));
    }
}
