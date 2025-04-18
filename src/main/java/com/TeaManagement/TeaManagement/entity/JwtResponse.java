package com.TeaManagement.TeaManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class JwtResponse {
    private User user;
    private String jwtToken;
}
