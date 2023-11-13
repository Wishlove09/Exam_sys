package edu.xmut.examsys.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 朔风
 * @date 2023-11-13 17:15
 */
public class SqlSessionFactoryUtils {

    private static SqlSessionFactory sqlSessionFactory;
    private static final Class CLASS_LOCK = SqlSessionFactoryUtils.class;

    public static SqlSession openSession(boolean isCommit) {
        if (sqlSessionFactory == null) {
            sqlSessionFactory = initSqlSessionFactory();
        }
        // 开启事务，自动提交
        return sqlSessionFactory.openSession(isCommit);
    }

    private static SqlSessionFactory initSqlSessionFactory() {
        String xml = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(xml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        synchronized (CLASS_LOCK) {
            if (sqlSessionFactory == null) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            }
        }
        return sqlSessionFactory;
    }

}
