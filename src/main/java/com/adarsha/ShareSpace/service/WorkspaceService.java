package com.adarsha.sharespace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.adarsha.sharespace.model.Workspace;
import com.adarsha.sharespace.repository.WorkspaceRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
                .files(new ArrayList<>())
                .createdAt(LocalDateTime.now())
                .build();

        return workspaceRepository.save(workspace);
    }

    public Workspace getWorkspace(String code) {
        return workspaceRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("WorkSpace not found"));
    }

    public Workspace updateText(String code, String newText) {

        Workspace workspace = workspaceRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Workspace not found"));
        workspace.setTextContent(newText);

        return workspaceRepository.save(workspace);
        
    }
    
}
