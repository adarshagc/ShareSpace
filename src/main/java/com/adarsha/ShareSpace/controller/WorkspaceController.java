package com.adarsha.ShareSpace.controller;

import com.adarsha.ShareSpace.model.Workspace;
import com.adarsha.ShareSpace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    
}
