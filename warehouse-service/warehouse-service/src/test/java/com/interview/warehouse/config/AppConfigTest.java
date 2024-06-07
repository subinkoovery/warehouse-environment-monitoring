package com.interview.warehouse.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AppConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testObjectMapperBean() {
        // Retrieve the ObjectMapper bean from the application context
        ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class);

        // Assert that the ObjectMapper bean is not null
        assertThat(objectMapper).isNotNull();
    }
}
