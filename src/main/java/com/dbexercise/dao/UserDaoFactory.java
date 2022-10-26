package com.dbexercise.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 제어권의 이전 main 함수에서 뭘쓸지 결정했다가 이제는 factory 함수에서 제어해준다.
// Spring cf.spring -boot 아님
@Configuration
public class UserDaoFactory {
    // 조립을 해준다
    @Bean
    public UserDao awsUserDao() { // 날개 5개 짜리
        AwsConnectionMaker awsConnectionMaker = new AwsConnectionMaker();
        // context 재사용하는 부분이 많은 코드
        UserDao userDao = new UserDao(awsConnectionMaker);
        return userDao;
    }

    @Bean
    public UserDao localUserDao() { // 날개 4개 짜리
        UserDao userDao = new UserDao(new LocalConnectionMaker());
        return userDao;
    }

}
