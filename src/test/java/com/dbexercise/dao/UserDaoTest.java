package com.dbexercise.dao;

import com.dbexercise.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {
    @Autowired // 빈복된는거
    ApplicationContext context;
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
        //UserDao userDao = new UserDao(new AwsConnectionMaker()); // factory 구현 후 없어짐
        //UserDao userDao = new UserDaoFactory().awsUserDao();// spring 사용 후 없어짐
        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
        UserDao userDao2 = context.getBean("awsUserDao", UserDao.class);
        // 같은 주소를 불러옴 왜? 싱글톤을 이용해 new 를 한번만 해서 불러오기 때문 // 참고로 new 하면 힙메모리에 쌓여
        System.out.println(userDao);
        System.out.println(userDao2);
        String id = "12";
        User user = new User(id, "min", "1111");
        userDao.add(user);

        User selectedUser = userDao.findById(id);
        Assertions.assertEquals("min", selectedUser.getName());
    }
}