package com.example.mobilear.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private Account account;
    private UserDetails userDetails;
}

