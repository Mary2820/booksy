package com.solvd.booksyapp.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.Reader;

public class ConnectionFactory {
    private static SqlSessionFactory sqlSessionFactory;
    private static final String CONFIG_FILE = "mybatis-config.xml";


    public ConnectionFactory() {
    }

    public static SqlSessionFactory getSQLSessionFactory() {
        if (sqlSessionFactory == null) {
            try (Reader reader = Resources.getResourceAsReader(CONFIG_FILE)) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            } catch (IOException e) {
                throw new RuntimeException("Error initializing MyBatis", e);
            }
        }
        return sqlSessionFactory;
    }
}

