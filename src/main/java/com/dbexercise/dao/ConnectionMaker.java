package com.dbexercise.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {
    Connection makeConnection() throws SQLException; // sql 만들다가 오류 날 수도 있으니까
}
