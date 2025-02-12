package com.nttest.nttest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CustomerRequest {

    private String username;
    private String password;
    private String name;

}
