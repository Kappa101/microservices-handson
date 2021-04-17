package com.kappa.microservices.productcompositeservice.api.composite;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Q.. what is Spring Webflux ?
 *    A: In spring boot 2.0(spring 5) built in support for Reactive Applications .
 *    Spring uses `Project Reactor` and comes with new webFramework called WEBFLUX
 *
 * Q.. What is Reactive web framework ?
 *     A: non-blocking , HTTP clients and services can be written
 *
 *  Q.. Spring boot MVC Vs WebFlux ?
 *      A: HTTP client in Webflux is `WebClient` and for Spring boot MVC is `RestTemplate`
 *
 * Q.. difference between @RestController Vs @Controller ?
 *      A: @Restcontroller =  @Controller + @ResponseBody | @Controller == @Component
 *
 * */




@Api(description = "REST API for composite product information.")
public interface ProductCompositeServiceController {

    /**
     * Sample usage:
     * <p>
     * curl -X POST $HOST:$PORT/product-composite \
     * -H "Content-Type: application/json" --data \
     * '{"productId":123,"name":"product 123","weight":123}'
     *
     * @param body
     */
    @ApiOperation(
            value = "${api.product-composite.create-composite-product.description}",
            notes = "${api.product-composite.create-composite-product.notes}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request, invalid format of the request. See response message for more information."),
            @ApiResponse(code = 422, message = "Unprocessable entity, input parameters caused the processing to fail. See response message for more information.")
    })
    @PostMapping(
            value = "/product-composite",
            consumes = "application/json")
    ResponseEntity<Void> createCompositeProduct(@RequestBody ProductAggregate body);

    /**
     * Sample usage: curl $HOST:$PORT/product-composite/1
     *
     * @param productId
     * @return the composite product info, if found, else null
     */
    @ApiOperation(
            value = "${api.product-composite.get-composite-product.description}",
            notes = "${api.product-composite.get-composite-product.notes}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request, invalid format of the request. See response message for more information."),
            @ApiResponse(code = 404, message = "Not found, the specified id does not exist."),
            @ApiResponse(code = 422, message = "Unprocessable entity, input parameters caused the processing to fail. See response message for more information.")
    })
    @GetMapping(
            value = "/product-composite/{productId}",
            produces = "application/json")
    Mono<ProductAggregate> getCompositeProduct(@PathVariable int productId);


    /**
     * Sample usage:
     * <p>
     * curl -X DELETE $HOST:$PORT/product-composite/1
     *
     * @param productId
     */
    @ApiOperation(
            value = "${api.product-composite.delete-composite-product.description}",
            notes = "${api.product-composite.delete-composite-product.notes}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request, invalid format of the request. See response message for more information."),
            @ApiResponse(code = 422, message = "Unprocessable entity, input parameters caused the processing to fail. See response message for more information.")
    })
    @DeleteMapping(value = "/product-composite/{productId}")
    ResponseEntity<Void> deleteCompositeProduct(@PathVariable int productId);
}
