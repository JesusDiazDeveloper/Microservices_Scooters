package com.arqui.integrador.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.arqui.integrador.model.Price;

public interface PriceRepository extends MongoRepository<Price, String> {
    
}
