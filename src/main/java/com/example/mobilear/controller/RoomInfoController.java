package com.example.mobilear.controller;

import com.example.mobilear.jwt.JwtTokenProvider;
import com.example.mobilear.service.RoomInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roomInfo")
public class RoomInfoController {

    @Autowired
    private RoomInfoService roomInfoService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/{roomId}")
    public ResponseEntity<?> getRoomInfo(@PathVariable String roomId) {
        return roomInfoService.getRoomInfo(roomId);
    }

    @PutMapping("/{roomId}/rename")
    public ResponseEntity<?> renameRoom(@PathVariable String roomId, @RequestParam String newName) {

        return roomInfoService.renameRoom(roomId, newName);
    }

    @PostMapping("/{roomId}/addModel")
    public ResponseEntity<?> addModel(@PathVariable String roomId, @RequestParam String modelId) {
        return roomInfoService.addModel(roomId, modelId);
    }

    @DeleteMapping("/{roomId}/removeModel")
    public ResponseEntity<?> deleteModel(@PathVariable String roomId, @RequestParam String modelId) {
        return roomInfoService.deleteModel(roomId, modelId);
    }

    @GetMapping("/allRoomsByUser")
    public ResponseEntity<?> getAllRoomsByUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String userId = jwtTokenProvider.getUsernameFromJwt(token);
        return roomInfoService.getAllRoomsByUser(userId);
    }
}
