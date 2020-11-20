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
public class MQConfig {

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

    //exchenges
    @Bean
    public DirectExchange directExchange1() {
        return new DirectExchange("request");
    }

    @Bean
    public DirectExchange directExchange2() {
        return new DirectExchange("response");
    }

    //request client Queues
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


    //response client Queues

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


    // bindings for client request
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


    // bindings for client response

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


    //request product Queues
    @Bean
    public Queue productRequestQueueAll() {
        return new Queue("request.products.all");
    }

    @Bean
    public Queue productRequestQueueById() {
        return new Queue("request.products.id");
    }

    @Bean
    public Queue productRequestQueueAdd() {
        return new Queue("request.products.add");
    }

    @Bean
    public Queue productRequestQueueUpdate() {
        return new Queue("request.products.update");
    }

    @Bean
    public Queue productRequestQueueDelete() {
        return new Queue("request.products.delete");
    }

    //response product Queues
    @Bean
    public Queue productResponseQueueAll() {
        return new Queue("response.products.all");
    }

    @Bean
    public Queue productResponseQueueById() {
        return new Queue("response.products.id");
    }

    @Bean
    public Queue productResponseQueueAdd() {
        return new Queue("response.products.add");
    }

    @Bean
    public Queue productResponseQueueUpdate() {
        return new Queue("response.products.update");
    }

    @Bean
    public Queue productResponseQueueDelete() {
        return new Queue("response.products.delete");
    }

    // bindings for product request
    @Bean
    public Binding productsGetAll() {
        return BindingBuilder.bind(productRequestQueueAll()).to(directExchange1()).with("products.all");
    }

    @Bean
    public Binding productsGetById() {
        return BindingBuilder.bind(productRequestQueueById()).to(directExchange1()).with("products.id");
    }

    @Bean
    public Binding productsAdd() {
        return BindingBuilder.bind(productRequestQueueAdd()).to(directExchange1()).with("products.add");
    }

    @Bean
    public Binding productsUpdate() {
        return BindingBuilder.bind(productRequestQueueUpdate()).to(directExchange1()).with("products.update");
    }

    @Bean
    public Binding productsDelete() {
        return BindingBuilder.bind(productRequestQueueDelete()).to(directExchange1()).with("products.delete");
    }

    // bindings for product response
    @Bean
    public Binding productssResponseAll() {
        return BindingBuilder.bind(productResponseQueueAll()).to(directExchange2()).with("products.all");
    }

    @Bean
    public Binding productssResponseById() {
        return BindingBuilder.bind(productResponseQueueById()).to(directExchange2()).with("products.id");
    }

    @Bean
    public Binding productssResponseAdd() {
        return BindingBuilder.bind(productResponseQueueAdd()).to(directExchange2()).with("products.add");
    }

    @Bean
    public Binding productssResponseUpdate() {
        return BindingBuilder.bind(productResponseQueueUpdate()).to(directExchange2()).with("products.update");
    }

    @Bean
    public Binding productssResponseDelete() {
        return BindingBuilder.bind(productResponseQueueDelete()).to(directExchange2()).with("products.delete");
    }


}
