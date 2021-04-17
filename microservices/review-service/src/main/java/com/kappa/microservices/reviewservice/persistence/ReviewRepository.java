package com.kappa.microservices.reviewservice.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * what is CrudRepository ? what is K , V ?
 * what is @Transactional(readonly=true) ?
 *
 *
 *
 * */

public interface ReviewRepository extends CrudRepository<ReviewEntity, Integer> {


    @Transactional(readOnly = true)
    List<ReviewEntity> findByProductId(int productId);


}
