package com.kappa.microservices.recommendationservice.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 * why are we not using @Repository here ?
 * how is ReactiveCrudRepository different from JpaRepository ?
 *
 *
 * */

public interface RecommendationRepository extends ReactiveCrudRepository<RecommendationEntity, String> {

    Flux<RecommendationEntity> findByProductId(int productId);

}




/*public interface RecommendationRepository extends CrudRepository<RecommendationEntity, String> {

    List<RecommendationEntity> findByProductId(int productId);
    
}*/
