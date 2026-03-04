package com.adarsha.sharespace.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.adarsha.sharespace.model.Workspace;

import java.util.Optional;

public interface WorkspaceRepository extends MongoRepository<Workspace, String> {
    
    Optional<Workspace> findByCode(String code);

    boolean existsByCode(String code);
}
