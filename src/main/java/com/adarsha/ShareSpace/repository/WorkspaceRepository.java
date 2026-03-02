package com.adarsha.ShareSpace.repository;

import com.adarsha.ShareSpace.model.Workspace;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WorkspaceRepository extends MongoRepository<Workspace, String> {
    
    Optional<Workspace> findByCode(String code);

    boolean existsByCode(String code);
}
