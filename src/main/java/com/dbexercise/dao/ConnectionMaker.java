package com.dbexercise.dao;

import java.sql.Connection;
import java.sql.SQLException;

// 인터페이스를 구현하는 모든 구현체를 사용할 수 있다.
public interface ConnectionMaker {
    Connection makeConnection() throws SQLException; // sql 만들다가 오류 날 수도 있으니까
}
