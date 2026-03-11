package com.adarsha.sharespace.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class WorkspaceResponse {
    
    private String code;
    private String textContent;
    private List<FileResponse> files;
    private LocalDateTime createdAt;
}
