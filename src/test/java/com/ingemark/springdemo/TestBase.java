package com.ingemark.springdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ingemark.springdemo.config.TestListenerService;
import com.ingemark.springdemo.utils.DatabaseCleanerService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.SQLException;

@SpringBootTest
@AutoConfigureMockMvc
public class TestBase {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected TestListenerService testListenerService;

    @Autowired
    private DatabaseCleanerService databaseCleanerService;

    @BeforeEach
    void cleanUp() throws SQLException {
        databaseCleanerService.deleteAll();
        testListenerService.clear();
    }
}
