package com.example.demo.dto.user;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ResponseUser implements Serializable {

    private Integer id;

    private String username;

    private String password;

    private String loginId;
    private String age;
    private String gender;
    private String userDesc;
    private LocalDateTime expiryDate;
    private String createName;
    private LocalDateTime createTime;
    private String updateName;
    private LocalDateTime updateTime;


    private String roleCode;

    private String roleName;

    private String status;

}
