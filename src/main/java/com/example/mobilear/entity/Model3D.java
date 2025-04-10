package com.example.mobilear.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Model3D {
    private String modelName;
    private String Category;
    private String modelUrl;
    private String thumbnailUrl;
    private String createdBy; // username của người dùng đã tạo mô hình
    private String createdAt;
}
