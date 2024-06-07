package com.interview.central_service.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class RabbitMQConfigTest {

    @InjectMocks
    private RabbitMQConfig rabbitMQConfig;

    @Mock
    private ConnectionFactory connectionFactory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testJsonMessageConverter() {
        MessageConverter messageConverter = rabbitMQConfig.jsonMessageConverter();
        assertThat(messageConverter).isInstanceOf(Jackson2JsonMessageConverter.class);
    }

    @Test
    public void testRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = (SimpleRabbitListenerContainerFactory) rabbitMQConfig.rabbitListenerContainerFactory(connectionFactory);
        assertThat(factory).isNotNull();
    }

    @Test
    public void testRabbitTemplate() {
        Jackson2JsonMessageConverter messageConverter = mock(Jackson2JsonMessageConverter.class);
        RabbitTemplate rabbitTemplate = rabbitMQConfig.rabbitTemplate(connectionFactory, messageConverter);
        assertThat(rabbitTemplate).isNotNull();
        assertThat(rabbitTemplate.getMessageConverter()).isEqualTo(messageConverter);
        assertThat(rabbitTemplate.getConnectionFactory()).isEqualTo(connectionFactory);
    }
}
