package com.example.mobilear.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Model3D {
    private String modelName;
    private String category;
    private String modelUrl;
    private String thumbnailUrl;
    private String createdBy; // username của người dùng đã tạo mô hình
    private String createdAt = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
}
