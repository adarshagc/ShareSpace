package com.adarsha.sharespace.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.adarsha.sharespace.service.WorkspaceService;
import com.adarsha.sharespace.dto.TextUpdateRequest;
import com.adarsha.sharespace.dto.WorkspaceResponse;
import com.adarsha.sharespace.model.Workspace;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/workspace")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Workspace API", description = "Operation related to workspace sharing")
public class WorkspaceController {


    private final WorkspaceService workspaceService;

    //Create Workspace

    @Operation(summary = "Create a new workspace") 
    @PostMapping("/{code}")
    public WorkspaceResponse createWorkspace(@PathVariable String code) {    
        return workspaceService.createWorkspaceResponse(code);
    }

    //Get Workspace

    @Operation(summary = "Get workspace by code")
    @GetMapping("/{code}")
    public WorkspaceResponse getWorkspace(@PathVariable String code) {
        return workspaceService.getWorkspaceByCode(code);
    }

    //Update Text

    @Operation(summary = "Update workspace text content")
    @PutMapping("/{code}/text")
    public WorkspaceResponse updateText(
            @PathVariable String code,
            @RequestBody TextUpdateRequest request) {
                
        return workspaceService.updateText(code, request.getTextContent());
    }

    //Upload File

    @Operation(summary = "Upload a file to workspace")
    @PostMapping("/{code}/upload")
    public Workspace uploadFile(@PathVariable String code, @RequestParam("file") MultipartFile file) throws Exception {

        return workspaceService.uploadFile(code, file);
    }

    //Download File

    @Operation(summary = "Download a file from workspace")
    @GetMapping("/files/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws Exception {
        Path filePath = Paths.get("uploads").resolve(filename);
        Resource resource = new UrlResource(filePath.toUri());

        if(!resource.exists()) {
            throw new RuntimeException("File not found: " + filename);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

    }

    //Delete File
    
    @Operation(summary = "Delete a file from workspace")
    @DeleteMapping("/{code}/files/{filename}")
    public Workspace deleteFile(@PathVariable String code,
                                @PathVariable String filename) throws Exception {
        
        return workspaceService.deleteFile(code, filename);
    }
}
