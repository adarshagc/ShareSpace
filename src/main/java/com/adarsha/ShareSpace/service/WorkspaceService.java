package com.adarsha.sharespace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.adarsha.sharespace.dto.FileResponse;
import com.adarsha.sharespace.dto.WorkspaceResponse;
import com.adarsha.sharespace.model.FileMeta;
import com.adarsha.sharespace.model.Workspace;
import com.adarsha.sharespace.repository.WorkspaceRepository;

import java.nio.file.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    public Workspace createWorkspace(String code) {

        if(workspaceRepository.existsByCode(code)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Workspace code already exists!");
        }

        Workspace workspace = Workspace.builder()
                .code(code)
                .textContent("")
                .files(new ArrayList<>())
                .createdAt(LocalDateTime.now())
                .build();

        return workspaceRepository.save(workspace);
    }

    public WorkspaceResponse createWorkspaceResponse() {
        Workspace workspace = createWorkspace();

        return mapToResponse(workspace);
    }

    public WorkspaceResponse getWorkspace(String code) {
        Workspace workspace = workspaceRepository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workspace not found"));

        return mapToResponse(workspace);
    }

    public WorkspaceResponse updateText(String code, String newText) {

        Workspace workspace = workspaceRepository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workspace not found"));
        
        workspace.setTextContent(newText);

        workspaceRepository.save(workspace);

        return mapToResponse(workspace);
        
    }

    public Workspace uploadFile(String code, MultipartFile file) throws IOException {

        Workspace workspace = workspaceRepository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workspace not found"));

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

    public Workspace deleteFile(String code, String filename) throws Exception {
        Workspace workspace = workspaceRepository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workspace not found"));
        
        Path filePath = Paths.get("uploads").resolve(filename);
        Files.deleteIfExists(filePath);

        workspace.getFiles().removeIf(file -> file.getFilePath().contains(filename));

        return workspaceRepository.save(workspace);
    }

    private WorkspaceResponse mapToResponse(Workspace workspace) {

        List<FileResponse> fileResponses = workspace.getFiles()
                .stream()
                .map(file -> FileResponse.builder()
                        .fileName(file.getFileName())
                        .uploadedAt(file.getUploadedAt())
                        .build())
                .toList();

        return WorkspaceResponse.builder()
                .code(workspace.getCode())
                .textContent(workspace.getTextContent())
                .files(fileResponses)
                .createdAt(workspace.getCreatedAt())
                .build();
    }
}
