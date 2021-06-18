package com.example.lab.vo;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LoginVO {
    private String username;
    private String password;
}
