package com.adarsha.sharespace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;


import com.adarsha.sharespace.service.WorkspaceService;
import com.adarsha.sharespace.dto.TextUpdateRequest;
import com.adarsha.sharespace.model.Workspace;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/workspace")
@RequiredArgsConstructor
@CrossOrigin
public class WorkspaceController {


    private final WorkspaceService workspaceService;

        
    @PostMapping
    public Workspace createWorkspace(@RequestParam String code) {    
        return workspaceService.createWorkspace(code);
    }

    @GetMapping("/{code}")
    public Workspace getWorkspace(@PathVariable String code) {
        return workspaceService.getWorkspace(code);
    }

    @PutMapping("/{code}/text")
    public Workspace updateText(@PathVariable String code,
                                @RequestBody TextUpdateRequest request) {
        return workspaceService.updateText(code, request.getTextContent());
    }

    //

    @PostMapping("/{code}/upload")
    public Workspace uploadFile(@PathVariable String code, @RequestParam("file") MultipartFile file) throws Exception {

        return workspaceService.uploadFile(code, file);
    }

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
    
}
