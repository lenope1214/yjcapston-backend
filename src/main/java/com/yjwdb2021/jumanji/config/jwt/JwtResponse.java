package com.yjwdb2021.jumanji.config.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String access_token;
    private String role;
}