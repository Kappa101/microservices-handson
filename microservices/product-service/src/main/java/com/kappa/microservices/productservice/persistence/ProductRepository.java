package com.kappa.microservices.productservice.persistence;

//import org.springframework.data.repository.PagingAndSortingRepository;
//import java.util.Optional;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;


/**
 * what is ReactiveCrudRepository<K , V> ? define K , V ?
 *  - K is the entity and V is the ID
 *
 * what is Mono here ?
 *
 *
 */
public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, String> {
    Mono<ProductEntity> findByProductId(int productId);
}



/*public interface ProductRepository rextends PagingAndSortingRepository<ProductEntity, String> {

    Optional<ProductEntity> findByProductId(int productId);
}*/
