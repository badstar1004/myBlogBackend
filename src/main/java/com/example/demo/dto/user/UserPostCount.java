package com.example.demo.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPostCount {

    private int userId;
    private int postCount;
}
