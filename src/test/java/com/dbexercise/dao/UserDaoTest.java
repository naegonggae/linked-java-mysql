package com.dbexercise.dao;

import com.dbexercise.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {
    @Autowired // 빈복된는거
    ApplicationContext context;

    UserDao userDao;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp() {
        this.userDao = context.getBean("awsUserDao", UserDao.class);
        this.user1 = new User("1", "lee", "1111");
        this.user2 = new User("2", "sang", "3333");
        this.user3 = new User("3", "hun", "2222");

    }
    @Test
    void addAndSelect() throws SQLException {
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
        //UserDao userDao2 = context.getBean("awsUserDao", UserDao.class);
        // 같은 주소를 불러옴 왜? 싱글톤을 이용해 new 를 한번만 해서 불러오기 때문 // 참고로 new 하면 힙메모리에 쌓여
        //System.out.println(userDao);
        //System.out.println(userDao2);
        userDao.deleteAll();
        Assertions.assertEquals(0, userDao.getCount());
        /*
        String id = "13";
        User user = new User(id, "min", "1111");
        userDao.add(user);
        Assertions.assertEquals(1, userDao.getCount());

        User selectedUser = userDao.findById(id);
        Assertions.assertEquals("min", selectedUser.getName());
        */
        // test 리펙토링
        userDao.add(user1);
        Assertions.assertEquals(1, userDao.getCount());
        User user = userDao.findById(user1.getId());

        Assertions.assertEquals(user1.getName(), user.getName());
        Assertions.assertEquals(user1.getPassword(), user.getPassword());
    }

    @Test
    void count() throws SQLException {

        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
        userDao.deleteAll();
        Assertions.assertEquals(0, userDao.getCount());

        userDao.add(user1);
        Assertions.assertEquals(1, userDao.getCount());
        userDao.add(user2);
        Assertions.assertEquals(2, userDao.getCount());
        userDao.add(user3);
        Assertions.assertEquals(3, userDao.getCount());
    }
    @Test
    void findById() {
        assertThrows(EmptyResultDataAccessException.class, ()->{
            userDao.findById("30");
        });
    }
}