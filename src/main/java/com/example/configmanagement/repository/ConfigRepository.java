package com.example.configmanagement.repository;

import com.example.configmanagement.entity.Config;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface ConfigRepository extends MongoRepository<Config, String> {
    List<Config> findBySystemId(String systemID); // It will automatically understand to query upon systemid
}
