package com.adarsha.ShareSpace.service;

import com.adarsha.ShareSpace.model.Workspace;
import com.adarsha.ShareSpace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    public Workspace createWorkspace(String code) {

        if(workspaceRepository.existsByCode(code)) {
            throw new RuntimeException("Workspace code already exists!");
        }

        Workspace workspace = Workspace.builder()
                .code(code)
                .textContent("")
                .files(Collections.emptyList())
                .createdAt(LocalDateTime.now())
                .build();

        return workspaceRepository.save(workspace);
    }

    public Workspace getWorkspace(String code) {
        return workspaceRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("WorkSpace not found"));
    }
    
}
