package com.example.mobilear.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomInfo {
    private String roomName;
    private String createdBy;
    private List<String> modelInRoom;
}
