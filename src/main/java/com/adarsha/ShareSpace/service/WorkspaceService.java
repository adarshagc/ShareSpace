package com.adarsha.sharespace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adarsha.sharespace.model.FileMeta;
import com.adarsha.sharespace.model.Workspace;
import com.adarsha.sharespace.repository.WorkspaceRepository;

import java.nio.file.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;


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

    public Workspace uploadFile(String code, MultipartFile file) throws IOException {

        Workspace workspace = workspaceRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Workspace not found"));

        String uploadDir = "uploads/";
        String fileName = System.currentTimeMillis() +  "_" + file.getOriginalFilename();

        Path path = Paths.get(uploadDir + fileName);
        Files.copy(file.getInputStream(), path);

        FileMeta fileMeta = FileMeta.builder()
                .fileName(file.getOriginalFilename())
                .filePath(path.toString())
                .uploadedAt(java.time.LocalDateTime.now())
                .build();

        workspace.getFiles().add(fileMeta);

        return workspaceRepository.save(workspace);
    }

    public Workspace createWorkspace() {

        String code = UUID.randomUUID().toString().substring(0, 6);

        Workspace workspace = Workspace.builder()
                .code(code)
                .textContent("")
                .files(new ArrayList<>())
                .createdAt(LocalDateTime.now())
                .build();
        
        return workspaceRepository.save(workspace);
    }
    
}
