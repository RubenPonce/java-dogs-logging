package com.lambdaschool.dogsinitial;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class DogsinitialApplication
{
    public static final String EXCHANGE_NAME = "DogServer";
    public static final String QUEUE_DOGGO = "DoggyQueue";
    public static final String QUEUE_CHECK_BREED = "CheckBreedDog";
    static DogList ourDogList;
    public static void main(String[] args)
    {
        ourDogList = new DogList();
        SpringApplication.run(DogsinitialApplication.class, args);
    }
    @Bean
    public TopicExchange appExchange()
    {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue appQueueHigh()
    {
        return new Queue(QUEUE_CHECK_BREED);
    }

    @Bean
    public Binding declareBindingHigh()
    {
        return BindingBuilder.bind(appQueueHigh()).to(appExchange()).with(QUEUE_CHECK_BREED);
    }

    @Bean
    public Queue appQueueLow()
    {
        return new Queue(QUEUE_DOGGO);
    }

    @Bean
    public Binding declareBindingLow()
    {
        return BindingBuilder.bind(appQueueLow()).to(appExchange()).with(QUEUE_DOGGO);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }
}

