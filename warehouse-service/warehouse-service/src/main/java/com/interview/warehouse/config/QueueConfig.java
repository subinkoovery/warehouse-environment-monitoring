package com.interview.warehouse.config;

import com.interview.warehouse.model.SensorType;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static com.interview.warehouse.constants.Constants.*;

/**
 * Class for Queue config
 */
@Configuration
public class QueueConfig {


    @Bean
    public Queue humidityQueue() {
        return new Queue(HUMIDITY_QUEUE, true);
    }

    @Bean
    public Queue temperatureQueue() {
        return new Queue(TEMPERATURE_QUEUE, true);
    }

    @Bean
    public TopicExchange sensorTopicExchange() {
        return new TopicExchange(SENSOR_TOPIC);
    }

    @Bean
    public Binding humiditySensorBinding(Queue humidityQueue, TopicExchange sensorTopicExchange) {
        return BindingBuilder.
                bind(humidityQueue)
                .to(sensorTopicExchange)
                .with(SensorType.HUMIDITY);
    }

    @Bean
    public Binding temperatureSensorBinding(Queue temperatureQueue, TopicExchange sensorTopicExchange) {
        return BindingBuilder
                .bind(temperatureQueue)
                .to(sensorTopicExchange)
                .with(SensorType.TEMPERATURE);
    }

    @Primary
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}
