package com.kappa.microservices.productcompositeservice;

import com.kappa.microservices.productcompositeservice.services.ProductCompositeIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.LinkedHashMap;

/**
 * @SpringBootApplication : @EnableAutoConfiguration + @ComponentScan + @Configuration
 *
 * J2EE (later JEE ) - had deployment descriptors
 *   what are features of Spring boot ?
 *    a module in Spring [ 2014 ] ; convention over configuration ; AutoConfiguration ; standalone-jars (fat JAR)
 *
 *  Q..can you create your own configuration ?
 *    A: YES , you can always override configuration ;
 *      let's say u want to change your Tomcat server to Jetty , you can exclude it in pom.xml and add jetty
 *
 * Q.. what is AutoConfiguration ?
 *  A: spring boot looks for JAR files in classpath that it can configure automatically
 *
 * Q.. what is Component Scanning ?
 *  A: @Component , @Repository , @Service , @Controller , @RestController(all are @Component only).
 *      so when any class is annotated by above annotations , those are eligible for component scanning and they can be used in your application using @Autowired
 *
 * Q.. why constructor injection keeps the state of the variable immutable ?
 *  A: there are 3 types of injection : Field level ; setter level and constructor level
 *
 *
 * Q.. below There is a @ComponentScan even after you used @SpringBootApplication . what is the significance of it ?
 *  A: If you want to use the packages outside your application's package eg. utility component shared by multiple Spring Boot applications etc..
 *          and you can simply autowire them in your application
 *
 * Q.. what is Java Based Configuration ?
 *  A: first you need to annotate a class @Configuration and component scanning will pick it up
 *      forexample: you want to write a log before and after  request is handled , we can add a log filter
 *                  you can also place some configuration below as @SpringBootApplication implicitly has @Configuration
 *
 * Q.. what are starter dependencies ?
 *  A: these are convenient dependencies you can add into your build tool [ maven or gradle ]
 *
 * Q.. why is @LoadBalanced used below ?
 *  A:
 *
 * Q.. what is gitflow - workflow ?
 *  A: https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow
 *      https://github.com/trein/dev-best-practices/wiki/Git-Commit-Best-Practices
 *      https://github.blog/2015-01-21-how-to-write-the-perfect-pull-request/#approach-to-writing-a-pull-request
 *      https://github.blog/2015-01-21-how-to-write-the-perfect-pull-request/#responding-to-feedback
 *      https://github.blog/2015-01-21-how-to-write-the-perfect-pull-request/#offering-feedback
 *
 *  Q.. How should the microservices communicate ?
 *      A: It must only communicate through well-defined interfaces
 *          ex:- using synchronous services or preferably by sending messages to each other
 *          using APIs and message formats that are stable, well-documented,
 *          and evolve by following a defined versioning strategy
 *
 * Q.. why should the microservices be stateless ?
 *  A: usually in a microservice setup , you do not have single process of that application running
 *      you would have multiple instances preferably using docker containers and kubernetes orchestration mechanism
 *          usually with control loops like live-ness probes , readiness-probes and on a larger scale of things
 *          a service mesh which helps with circuit breaker , failover patterns etc...
 *      so , if the application was stateful , the requests should/would handle by that particular instance and can cause data corruption
 *
 *  Q.. why stateless Vs stateful ?
 *      A: https://www.xenonstack.com/insights/stateful-and-stateless-applications/
 *
 *  Q.. challenges we can face when using microservices ?
 *      A: -> Many small components that use synchronous communication can cause a chain of failure problem, especially under high load
 *         -> we need to keep config up to date all the time
 *         -> RCA would be difficult [ this can be mitigated by centralized logging system ]
 *         -> Analyzing hardware resources can be challenging for each microservice [ use Prometheus/Grafana ]
 *         -> Network glitches , network is unreliable , external systems can go down , systems can go down
 *              in e1 or dev environments : due to less PODs , it might take time for external systems to come up and respond back
 *              in e2 or SIT and pre-prod env : this is where we start to analyze how many PODs would be needed to scale out to match up real Prod env
 *         -> Automating all these above issues would be time consuming [ infrastructure around it ]
 *         ->
 *
 * Q.. what is centralized configuration ?
 *      A: traditionally, deployed together with its configuration,
 *          environment variables and/or files containing configuration information
 *          ex:- let's say i want to update some configuration ;
 *                  let's say you have changed the KAFKA configurations for a SIT environment ,
 *                      then if there is no automated way , we need to go and do it manually
 *                      this requires for us to know if let's say server IP has been changed !
 *          so, we would have different settings for different environments in ONE place
 *                  like dev , test , QA , prod etc...
 *
 * Q.. What are foundations of a system to be reactive ?
 *  A: -> system / set of microservices should be message-driven
 *          this makes them to elastic (scale up / scale down )
 *          resilient => tolerant to failures
 *          [ having fallbacks , if message processing fails , use dead letter queues [or differnet partitions]
 *          Elasticity + resilience makes the system Responsive [ able to respond in a timely fashion [ according to some SLA's ]]
 *
 *
 *
 *
 * */
@SpringBootApplication
//@ComponentScan("com.kappa")
public class ProductCompositeServiceApplication {

    /* for microservice to look up other microservices need to add below*/
    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        final WebClient.Builder builder = WebClient.builder();
        return builder;
    }

    @Autowired
    HealthAggregator healthAggregator;

    @Autowired
    ProductCompositeIntegration integration;


    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    ReactiveHealthIndicator coreServices() {
        ReactiveHealthIndicatorRegistry registry = new
                DefaultReactiveHealthIndicatorRegistry(new LinkedHashMap<>());

        registry.register("product", () -> integration.getProductHealth());
        registry.register("recommendation", () ->
                integration.getRecommendationHealth());
        registry.register("review", () -> integration.getReviewHealth());

        return new CompositeReactiveHealthIndicator(healthAggregator,
                registry);
    }


    public static void main(String[] args) {

        SpringApplication.run(ProductCompositeServiceApplication.class, args);

    }

}
