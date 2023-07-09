package com.emreyildirim.service;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
