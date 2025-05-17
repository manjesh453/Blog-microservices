package com.userservice.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String firstname;
    private String lastname;
    private String contactNumber;
    private String address;
    private String description;
}
