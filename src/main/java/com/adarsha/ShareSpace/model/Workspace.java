package com.adarsha.ShareSpace.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "workspaces")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Workspace {
    
    @Id
    private String id;

    private String code;

    private String textContent;

    private List<FileMeta> files;

    private LocalDateTime createdAt;
}
