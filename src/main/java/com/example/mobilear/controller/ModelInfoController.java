package com.example.mobilear.controller;

import com.example.mobilear.Response.EHttpStatus;
import com.example.mobilear.Response.Response;
import com.example.mobilear.jwt.JwtTokenProvider;
import com.example.mobilear.service.ModelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Response<?> getAllModels() {
        try {
            return new Response<>(EHttpStatus.OK, modelInfoService.getAllModels());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return new Response<>(EHttpStatus.INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/getModelByCategory")
    public Response<?> getModelByCategory(@RequestParam String category) {
        try {
            return new Response<>(EHttpStatus.OK, modelInfoService.getModelByCategory(category));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return new Response<>(EHttpStatus.INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/getPersonalModels")
    public Response<?> getPersonalModels(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String username = jwtTokenProvider.getUsernameFromJwt(token);
        try {
            return new Response<>(EHttpStatus.OK, modelInfoService.getPersonalModels(username));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return new Response<>(EHttpStatus.INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }
    }

}
