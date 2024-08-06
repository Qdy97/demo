package com.example.demo.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Role {

    private int id;

    private String roleCode;

    private String roleName;

    private String status;

    private String createName;

    private LocalDateTime createTime;

    private String updateName;

    private LocalDateTime updateTime;

}
