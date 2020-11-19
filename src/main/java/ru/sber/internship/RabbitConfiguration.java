package ru.sber.internship;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sber.internship.rabbitmq.utils.RoutingKey;

@EnableRabbit
@Configuration
public class RabbitConfiguration {

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory("localhost");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setExchange("exchange");
        return rabbitTemplate;
    }

    @Bean
    public Queue myQueue1() {
        return new Queue("request", true,false,false);
    }

    @Bean
    public Queue myQueue2() {
        return new Queue("response", true,false,false);
    }

    @Bean
    public DirectExchange directExchange1(){
        return new DirectExchange("request");
    }

    @Bean
    public DirectExchange directExchange2(){
        return new DirectExchange("response");
    }

    @Bean
    public Binding clientsGetAll(){
        return BindingBuilder.bind(myQueue1()).to(directExchange1()).with("clients.all");
    }

    @Bean
    public Binding clientsAdd(){
        return BindingBuilder.bind(myQueue1()).to(directExchange1()).with("clients.add");
    }




    @Bean
    public Binding clientsShowAll(){
        return BindingBuilder.bind(myQueue2()).to(directExchange2()).with("clients.all");
    }

    @Bean
    public Binding clientsShowAdd(){
        return BindingBuilder.bind(myQueue2()).to(directExchange2()).with("clients.add");
    }




//    @Bean
//    public Binding errorBinding2(){
//        return BindingBuilder.bind(myQueue2()).to(directExchange()).with("error");
//    }
//
//    @Bean
//    public Binding infoBinding(){
//        return BindingBuilder.bind(myQueue2()).to(directExchange()).with("info");
//    }
//
//    @Bean
//    public Binding warningBinding(){
//        return BindingBuilder.bind(myQueue2()).to(directExchange()).with("warning");
//    }
//
}
