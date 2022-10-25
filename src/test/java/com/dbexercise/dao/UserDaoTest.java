package com.dbexercise.dao;

import com.dbexercise.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    @Test
    void addAndSelect() {
        /*
        // Aws db 사용하니까 클래스명 참조형 변경. 추상클래스인 AwsUserDaoImpl 사용
        AwsUserDaoImpl userDao = new AwsUserDaoImpl();
        String id = "1";
        User user = new User(id, "min", "1111");
        userDao.add(user);

        User selectedUser = userDao.findById(id);
        Assertions.assertEquals("min", selectedUser.getName());
         */
        /*
        // 클래스로 분리했을때
        UserDao userDao = new UserDao();
        String id = "7";
        User user = new User(id, "min", "1111");
        userDao.add(user);

        User selectedUser = userDao.findById(id);
        Assertions.assertEquals("min", selectedUser.getName());
         */
        // 인터페이스 구현
        UserDao userDao = new UserDao(new AwsConnectionMaker());
        String id = "8";
        User user = new User(id, "min", "1111");
        userDao.add(user);

        User selectedUser = userDao.findById(id);
        Assertions.assertEquals("min", selectedUser.getName());
    }
}