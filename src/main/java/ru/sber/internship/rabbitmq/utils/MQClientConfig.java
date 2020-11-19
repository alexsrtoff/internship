package ru.sber.internship.rabbitmq.utils;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class MQClientConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    //request Queues
    @Bean
    public Queue clientRequestQueueAll() {
        return new Queue("request.clients.all");
    }

    @Bean
    public Queue clientRequestQueueAdd() {
        return new Queue("request.clients.add");
    }

    @Bean
    public Queue clientRequestQueueById() {
        return new Queue("request.clients.showById");
    }

    @Bean
    public Queue clientRequestQueueOrdersById() {
        return new Queue("request.clients.showOrdersById");
    }

    @Bean
    public Queue clientRequestQueueUpdate() {
        return new Queue("request.clients.update");
    }

    @Bean
    public Queue clientRequestQueueDelete() {
        return new Queue("request.clients.delete");
    }


    //response Queues

    @Bean
    public Queue clientResponseQueueAll() {
        return new Queue("response.clients.all");
    }

    @Bean
    public Queue clientResponseQueueAdd() {
        return new Queue("response.clients.add");
    }

    @Bean
    public Queue clientResponseQueueById() {
        return new Queue("response.clients.showById");
    }

    @Bean
    public Queue clientResponseQueueOrdersById() {
        return new Queue("response.clients.showOrdersById");
    }

    @Bean
    public Queue clientResponseQueueUpdate() {
        return new Queue("response.clients.update");
    }

    @Bean
    public Queue clientResponseQueueDelete() {
        return new Queue("response.clients.delete");
    }

    //exchenges
    @Bean
    public DirectExchange directExchange1() {
        return new DirectExchange("request");
    }

    @Bean
    public DirectExchange directExchange2() {
        return new DirectExchange("response");
    }

    // bindings for request
    @Bean
    public Binding clientsGetAll() {
        return BindingBuilder.bind(clientRequestQueueAll()).to(directExchange1()).with("clients.all");
    }

    @Bean
    public Binding clientsAdd() {
        return BindingBuilder.bind(clientRequestQueueAdd()).to(directExchange1()).with("clients.add");
    }

    @Bean
    public Binding clientsShowById() {
        return BindingBuilder.bind(clientRequestQueueById()).to(directExchange1()).with("clients.showById");
    }

    @Bean
    public Binding clientsShowOrdersById() {
        return BindingBuilder.bind(clientRequestQueueOrdersById()).to(directExchange1()).with("clients.showOrdersById");
    }

    @Bean
    public Binding clientsUpdate() {
        return BindingBuilder.bind(clientRequestQueueUpdate()).to(directExchange1()).with("clients.update");
    }

    @Bean
    public Binding clientDelete() {
        return BindingBuilder.bind(clientRequestQueueDelete()).to(directExchange1()).with("clients.delete");
    }


    // bindings for response

    @Bean
    public Binding clientsResponseAll() {
        return BindingBuilder.bind(clientResponseQueueAll()).to(directExchange2()).with("clients.all");
    }

    @Bean
    public Binding clientsResponseAdd() {
        return BindingBuilder.bind(clientResponseQueueAdd()).to(directExchange2()).with("clients.add");
    }

    @Bean
    public Binding clientsResponseShowById() {
        return BindingBuilder.bind(clientResponseQueueById()).to(directExchange2()).with("clients.showById");
    }

    @Bean
    public Binding clientsResponseShowOrdersById() {
        return BindingBuilder.bind(clientResponseQueueOrdersById()).to(directExchange2()).with("clients.showOrdersById");
    }

    @Bean
    public Binding clientsResponseUpdate() {
        return BindingBuilder.bind(clientResponseQueueUpdate()).to(directExchange2()).with("clients.update");
    }

    @Bean
    public Binding clientsDelete() {
        return BindingBuilder.bind(clientResponseQueueDelete()).to(directExchange2()).with("clients.delete");
    }

}
