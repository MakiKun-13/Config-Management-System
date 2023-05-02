package com.example.configmanagement.repository;

import com.example.configmanagement.entity.ClientSystem;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface SystemRepository extends MongoRepository<ClientSystem, String> {
}
