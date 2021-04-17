package com.kappa.microservices.reviewservice.services;

import com.kappa.microservices.reviewservice.api.core.review.Review;
import com.kappa.microservices.reviewservice.api.core.review.ReviewService;
import com.kappa.microservices.reviewservice.api.event.Event;
import com.kappa.microservices.reviewservice.util.exceptions.EventProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
/**
 *
 * Q.. which library  are you using for event-driven async ?
 *  A: Spring cloud stream [ spring-cloud-starter-stream-kafka ]
 *
 *
 * Q.. what is @EnableBinding(Sink.class)
 *
 * Q.. what is @StreamListener(target = Sink.INPUT) ?
 *  A: this annotation is independent of underlying message broker
 *
 * Q.. what are consumer groups ?
 *  A: it solves the issue of one instance of a particular microservice per consumer to process each message
 *
 *  Q.. how do you configure a consumer ?
 *      A: This usually goes into consul configuration server
 *      spring.cloud.stream:
 *              bindings.input:
 *              destination: products   // this is the topic to which messages are sent
 *              group: productsGroup    // this is the consumer group to which your microservice will bind
 *     so any messages are sent to products topic , kafka will make sure it will send the messages to one of the instances
 *          of productsGroup consumer group
 *
 *   Q.. what are retries and DLQ ?
 *      A: if a consumer fails to process a message , it may be lost or re-queued for failing consumer until it becomes successfuly processed
 *          If a processing fails due to network glitch , you can use retries
 *          so even after retries the message process fails , u move it to another topic /queue for fault analysis or corrections
 *
 *   Q.. how do you enable deadletterqueues ?
 *      A:  spring.cloud.stream.bindings.input.consumer:
 *           maxAttempts: 3             // retries before placing the message in a dlq
 *           backOffInitialInterval: 500
 *           backOffMaxInterval: 1000
 *           backOffMultiplier: 2.0
 *
 *          spring.cloud.stream.kafka.bindings.input.consumer:
 *          enableDlq: true
 *
 *
 * Q.. what are dead-letter-queues ?
 *  A: it is where the failed messages are stored for later fault enquiry or any corrections
 *
 *
 * Q.. what are partitions ?
 *  A: Partitions ensure that the messages that are published are put in same order and sent to subscribers
 *      without losing upon scalability and performance
 *
 * Q.. what all are required (classes , dependencies , configuration etc .. ) for event driven system ?
 *  A: classes : if it is a publisher or subscriber both should have a Event<K,V> model class
 *      dependencies : spring-cloud-starter-stream-kafka [module] ; spring-cloud-stream-test-support [ test module ]
 *                      declare cloud version : <spring-cloudstream.version>Hoxton.SR6</spring-cloudstream.version>
 *
 *     configuration: given above
 *
 * Q.. How to declare Message Sources and publishing events in Integration layers ?
 *  A: @EnableBinding(ProductCompositeIntegration.MessageSources.class)
 *      the above annotation says that
 *          whenever we want to publish an event on a topic
 *
 * Q.. To create the message that contains the event, we can use the built-in MessageBuilder class
 *
 * Q.. what are binders ?
 *  A:  default.contentType: application/json # means message content in JSON
 *
 *      spring.cloud.stream.kafka.binder:
 *      brokers: 127.0.0.1              # usually we will have a hostname(HA kubernetes services objects [intranet])
 *      defaultBrokerPort: 9092
 *
 * Q.. TestSupportBinder ?
 *  A: SPring boot comes with this helper Test class
 *      this helps to see which message is being sent out without having message broker in place
 *
 *  Q.. what is @EnableBinding(Sink.class) ?
 *    A: Tells boot to use Sink class , which means this is consumer
 *
 *  Q.. @StreamListener(target = Sink.INPUT) ?
 *      A:  when you annotate a method with above , it consumes and processes messages
 *          usually it consists of an Event model class as parameter
 *          and depending on Event type one of the corresponding methods are invoked
 *
 *  Q.. why did you choose KAFKA over rabbitMq ?
 *  A: When using Spring Cloud Stream with Kafka,
 *          events are retained in the topics, even after consumers have processed them.
 *      but in rabbitMQ , you need to create and manage and maintain different queues
 *
 *  Q..
 * */



@EnableBinding(Sink.class)
public class MessageProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(MessageProcessor.class);

    private final ReviewService reviewService;

    @Autowired
    public MessageProcessor(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @StreamListener(target = Sink.INPUT)
    public void process(Event<Integer, Review> event) {

        LOG.info("Process message created at {}...", event.getEventCreatedAt());

        switch (event.getEventType()) {

            case CREATE:
                Review review = event.getData();
                LOG.info("Create review with ID: {}/{}", review.getProductId(), review.getReviewId());
                reviewService.createReview(review);
                break;

            case DELETE:
                int productId = event.getKey();
                LOG.info("Delete reviews with ProductID: {}", productId);
                reviewService.deleteReviews(productId);
                break;

            default:
                String errorMessage = "Incorrect event type: " + event.getEventType() + ", expected a CREATE or DELETE event";
                LOG.warn(errorMessage);
                throw new EventProcessingException(errorMessage);
        }

        LOG.info("Message processing done!");
    }
}
