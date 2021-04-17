package com.kappa.microservices.reviewservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

/**
 * Q.. what is @Value ? what is max-pool-size below ?
 *  A: @Value in springboot annotations , would get the values from configuration file(app*.yml)
 *      so if there is no value given in the spring boot app.yml or app*.properties ,
 *          we can give out a default value for it
 *
 * Q.. what is JdbcScheduler ?
 *  A:
 *
 *
 * */
@SpringBootApplication
@ComponentScan("com.kappa")
public class ReviewServiceApplication {

    private static final Logger LOG = LoggerFactory.getLogger(ReviewServiceApplication.class);

    private final Integer connectionPoolSize;

    @Autowired
    public ReviewServiceApplication(
            @Value("${spring.datasource.maximum-pool-size:10}")
                    Integer connectionPoolSize
    ) {
        this.connectionPoolSize = connectionPoolSize;
    }

    @Bean
    public Scheduler jdbcScheduler() {
        LOG.info("Creates a jdbcScheduler with connectionPoolSize = " + connectionPoolSize);
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(connectionPoolSize));
    }

    public static void main(String[] args) {
        SpringApplication.run(ReviewServiceApplication.class, args);
        System.out.println(Runtime.getRuntime().availableProcessors());

    }


}
