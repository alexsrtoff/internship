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
    public Binding productsResponseAll() {
        return BindingBuilder.bind(productResponseQueueAll()).to(directExchange2()).with("products.all");
    }

    @Bean
    public Binding productsResponseById() {
        return BindingBuilder.bind(productResponseQueueById()).to(directExchange2()).with("products.id");
    }

    @Bean
    public Binding productsResponseAdd() {
        return BindingBuilder.bind(productResponseQueueAdd()).to(directExchange2()).with("products.add");
    }

    @Bean
    public Binding productsResponseUpdate() {
        return BindingBuilder.bind(productResponseQueueUpdate()).to(directExchange2()).with("products.update");
    }

    @Bean
    public Binding productsResponseDelete() {
        return BindingBuilder.bind(productResponseQueueDelete()).to(directExchange2()).with("products.delete");
    }

    //request order Queues
    @Bean
    public Queue orderRequestQueueAll() {
        return new Queue("request.orders.all");
    }


    @Bean
    public Queue orderRequestQueueById() {
        return new Queue("request.orders.id");
    }

    @Bean
    public Queue orderRequestQueueProductsByOrderId() {
        return new Queue("request.orders.products.orderId");
    }

    @Bean
    public Queue orderRequestQueueClientByOrderId() {
        return new Queue("request.orders.client.orderId");
    }

    @Bean
    public Queue orderRequestQueueItemstByOrderId() {
        return new Queue("request.orders.items.orderId");
    }

    @Bean
    public Queue orderRequestQueueAdd() {
        return new Queue("request.orders.add");
    }

    @Bean
    public Queue orderRequestQueueUpdate() {
        return new Queue("request.orders.update");
    }

    @Bean
    public Queue orderRequestQueueDelete() {
        return new Queue("request.orders.delete");
    }

    @Bean
    public Queue orderRequestQueueDeleteByClienId() {
        return new Queue("request.orders.delete.clientId");
    }


    //response order Queues
    @Bean
    public Queue orderResponseQueueAll() {
        return new Queue("response.orders.all");
    }

    @Bean
    public Queue orderResponseQueueById() {
        return new Queue("response.orders.id");
    }

    @Bean
    public Queue orderResponseQueueProductsByOrderId() {
        return new Queue("response.orders.products.orderId");
    }

    @Bean
    public Queue orderResponseQueueClientByOrderId() {
        return new Queue("response.orders.client.orderId");
    }

    @Bean
    public Queue orderResponseQueueItemsByOrderId() {
        return new Queue("response.orders.items.orderId");
    }

    @Bean
    public Queue orderResponseQueueAdd() {
        return new Queue("response.orders.add");
    }

    @Bean
    public Queue orderResponseQueueUpdate() {
        return new Queue("response.orders.update");
    }

    @Bean
    public Queue orderResponseQueueDelete() {
        return new Queue("response.orders.delete");
    }

    @Bean
    public Queue orderResponseQueueDeleteByClientId() {
        return new Queue("response.orders.delete.clientId");
    }


    // bindings for order request
    @Bean
    public Binding ordersGetAll() {
        return BindingBuilder.bind(orderRequestQueueAll()).to(directExchange1()).with("orders.all");
    }

    @Bean
    public Binding ordersGetById() {
        return BindingBuilder.bind(orderRequestQueueById()).to(directExchange1()).with("orders.id");
    }

    @Bean
    public Binding ordersGetProductsByOrderId() {
        return BindingBuilder.bind(orderRequestQueueProductsByOrderId()).to(directExchange1()).with("orders.products.orderId");
    }

    @Bean
    public Binding ordersGetClientByOrderId() {
        return BindingBuilder.bind(orderRequestQueueClientByOrderId()).to(directExchange1()).with("orders.client.orderId");
    }

    @Bean
    public Binding ordersGetItemsByOrderId() {
        return BindingBuilder.bind(orderRequestQueueItemstByOrderId()).to(directExchange1()).with("orders.items.orderId");
    }

    @Bean
    public Binding ordersAdd() {
        return BindingBuilder.bind(orderRequestQueueAdd()).to(directExchange1()).with("orders.add");
    }

    @Bean
    public Binding ordersUpdate() {
        return BindingBuilder.bind(orderRequestQueueUpdate()).to(directExchange1()).with("orders.update");
    }

    @Bean
    public Binding ordersDelete() {
        return BindingBuilder.bind(orderRequestQueueDelete()).to(directExchange1()).with("orders.delete");
    }

    @Bean
    public Binding ordersDeleteByclientId() {
        return BindingBuilder.bind(orderRequestQueueDeleteByClienId()).to(directExchange1()).with("orders.delete.clientId");
    }

    // bindings for order response
    @Bean
    public Binding ordersResponseAll() {
        return BindingBuilder.bind(orderResponseQueueAll()).to(directExchange2()).with("orders.all");
    }

    @Bean
    public Binding ordersResponseById() {
        return BindingBuilder.bind(orderResponseQueueById()).to(directExchange2()).with("orders.id");
    }

    @Bean
    public Binding ordersResponseProductsByOrderId() {
        return BindingBuilder.bind(orderResponseQueueProductsByOrderId()).to(directExchange2()).with("orders.products.orderId");
    }

    @Bean
    public Binding ordersResponseClientByOrderId() {
        return BindingBuilder.bind(orderResponseQueueClientByOrderId()).to(directExchange2()).with("orders.client.orderId");
    }

    @Bean
    public Binding ordersResponseItemsByOrderId() {
        return BindingBuilder.bind(orderResponseQueueItemsByOrderId()).to(directExchange2()).with("orders.items.orderId");
    }

    @Bean
    public Binding ordersResponseAdd() {
        return BindingBuilder.bind(orderResponseQueueAdd()).to(directExchange2()).with("orders.add");
    }

    @Bean
    public Binding ordersResponseUpdate() {
        return BindingBuilder.bind(orderResponseQueueUpdate()).to(directExchange2()).with("orders.update");
    }

    @Bean
    public Binding ordersResponseDelete() {
        return BindingBuilder.bind(orderResponseQueueDelete()).to(directExchange2()).with("orders.delete");
    }

    @Bean
    public Binding ordersResponseDeleteByClientId() {
        return BindingBuilder.bind(orderResponseQueueDeleteByClientId()).to(directExchange2()).with("orders.delete.clientId");
    }


}
