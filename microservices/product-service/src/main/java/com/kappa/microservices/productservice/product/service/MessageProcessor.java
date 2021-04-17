package com.kappa.microservices.productservice.product.service;


import com.kappa.microservices.productservice.api.core.product.Product;
import com.kappa.microservices.productservice.api.core.product.ProductService;
import com.kappa.microservices.productservice.api.event.Event;
import com.kappa.microservices.productservice.util.exceptions.EventProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
/**
 * Q.. when to choose non-blocking synchronous APIs and when to choose Asynchronous ?
 *  A: you need to make your system autonomous ,
 *
 * Q.. what did you use for your protocols , RESTAPI etc... ?
 *  A: for reads , we are using synchronous non-blocking APIs using @Webclient ,
 *     for create or state update events , we are using kafka streaming
 *
 * Q.. How do you test your asynchronous code ?
 *  A: we use StepVerifier helper class as it contains helper methods to achieve async tests
 *
 * Q.. what is @PathVariable ?
 *  A: to address the path variable
 *
 *
 * */

@EnableBinding(Sink.class)  //
public class MessageProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(MessageProcessor.class);

    private final ProductService productService;

    @Autowired
    public MessageProcessor(ProductService productService) {
        this.productService = productService;
    }

    @StreamListener(target = Sink.INPUT)
    public void process(Event<Integer, Product> event) {

        LOG.info("Process message created at {}...", event.getEventCreatedAt());

        switch (event.getEventType()) {

            case CREATE:
                Product product = event.getData();
                LOG.info("Create product with ID: {}", product.getProductId());
                productService.createProduct(product);
                break;

            case DELETE:
                int productId = event.getKey();
                LOG.info("Delete recommendations with ProductID: {}", productId);
                productService.deleteProduct(productId);
                break;

            default:
                String errorMessage = "Incorrect event type: " + event.getEventType() + ", expected a CREATE or DELETE event";
                LOG.warn(errorMessage);
                throw new EventProcessingException(errorMessage);
        }

        LOG.info("Message processing done!");
    }
}
