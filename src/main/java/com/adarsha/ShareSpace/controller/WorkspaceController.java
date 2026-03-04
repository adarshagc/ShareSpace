package com.adarsha.sharespace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.adarsha.sharespace.model.Workspace;
import com.adarsha.sharespace.service.WorkspaceService;
import com.adarsha.sharespace.dto.TextUpdateRequest;

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
    
}
