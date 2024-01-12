package com.lms.coffeeshopclone;


import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootTest
public class DatabaseCleanUpExecutor {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // jdbc 초기화
    @AfterEach
    public void tearDown(){
        final List<String> truncateQueries = getTruncateQueries(jdbcTemplate);
        truncateTables(jdbcTemplate, truncateQueries);

    }

    private List<String> getTruncateQueries(final JdbcTemplate jdbcTemplate){

        // 테이블 초기화
        String sql = """
                 SELECT Concat('TRUNCATE TABLE `', table_name, '`;') AS q 
                 From information_schema.tables 
                 WHERE table_schema = 'PUBLIC'
                """;
        return jdbcTemplate.queryForList(sql, String.class);
    }

    private void truncateTables(final JdbcTemplate jdbcTemplate, final List<String> truncateQueries){

        // 데이터베이스 무결성 제약조건 비활성화
        execute(jdbcTemplate, "SET REFERENTIAL_INTEGRITY FALSE");
        // 테이블 비우기
        truncateQueries.forEach(query -> execute(jdbcTemplate, query));
        // 데이터베이스 무결성 제약조건 활성화
        execute(jdbcTemplate, "SET REFERENTIAL_INTEGRITY TRUE");

    }

    private void execute(final JdbcTemplate jdbcTemplate, final String query){
        jdbcTemplate.execute(query);
    }





}
