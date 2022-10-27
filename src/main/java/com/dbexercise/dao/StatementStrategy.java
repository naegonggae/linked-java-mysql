package com.dbexercise.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// strategy 패턴 인터페이스를 의존하게 해서 구현체를 필요에 따라 선택할 수 있게 하는 디자인 패턴
public interface StatementStrategy {
    PreparedStatement makePreparedStatement(Connection connection) throws SQLException;
}
