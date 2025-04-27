package com.example.mobilear.controller;

import com.example.mobilear.Response.EHttpStatus;
import com.example.mobilear.entity.Model3D;
import com.example.mobilear.jwt.JwtTokenProvider;
import com.example.mobilear.service.ModelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modelInfo")
public class ModelInfoController {
    private final ModelInfoService modelInfoService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public ModelInfoController(ModelInfoService modelInfoService) {
        this.modelInfoService = modelInfoService;
    }

    @GetMapping("getAllModels")
    public ResponseEntity<?> getAllModels() {
        try {
            return ResponseEntity.ok(modelInfoService.getAllModels());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(EHttpStatus.INTERNAL_SERVER_ERROR.getCode())
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/getSpecialOffers")
    public ResponseEntity<?> getSpecialOffers() {
        try {
            List<Model3D> specialOffers = modelInfoService.getSpecialOffers();
            return ResponseEntity.ok(specialOffers);
        } catch (Exception e) {
            return ResponseEntity.status(EHttpStatus.INTERNAL_SERVER_ERROR.getCode())
                    .body("Error fetching special offers: " + e.getMessage());
        }
    }


    @GetMapping("/getModelByCategory")
    public ResponseEntity<?> getModelByCategory(@RequestParam String category) {
        try {
            if (category == null || category.isEmpty()) {
                return ResponseEntity.status(EHttpStatus.BAD_REQUEST.getCode())
                        .body("Category cannot be null or empty");
            }
            return ResponseEntity.ok(modelInfoService.getModelByCategory(category));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(EHttpStatus.INTERNAL_SERVER_ERROR.getCode())
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/getPersonalModels")
    public ResponseEntity<?> getPersonalModels(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtTokenProvider.getUsernameFromJwt(token);
        try {
            return ResponseEntity.ok(modelInfoService.getPersonalModels(username));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(EHttpStatus.INTERNAL_SERVER_ERROR.getCode())
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/addModelInfo")
    public ResponseEntity<?> addModelInfo(@RequestBody Model3D modelInfo) {
        try {
            return modelInfoService.addModelInfo(modelInfo);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(EHttpStatus.INTERNAL_SERVER_ERROR.getCode())
                    .body("An error occurred: " + e.getMessage());
        }
    }

}
