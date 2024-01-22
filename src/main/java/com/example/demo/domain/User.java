package com.example.demo.domain;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class User {

    private Long userId;
    private String biography;
    private String profilePIC;
    private Timestamp lastLogin;
}
