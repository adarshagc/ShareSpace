package com.adarsha.sharespace.model;

import lombok.*;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileMeta {

    private String fileName;
    private String filePath;
    private LocalDateTime uploadedAt;
}
