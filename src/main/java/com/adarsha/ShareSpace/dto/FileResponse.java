package com.adarsha.sharespace.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FileResponse {
    
    private String fileName;
    private LocalDateTime uploadedAt;
}
