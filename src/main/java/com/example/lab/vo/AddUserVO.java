package com.example.lab.vo;

import lombok.*;

import javax.validation.constraints.Size;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AddUserVO {
    @Size(min = 2, max = 12,
            message = "您输入的值为${validatedValue}，用户名长度必须大于{min}，小于{max}")
    private String username;

    @Size(min = 2, max = 18,
            message = "您输入的值为${validatedValue}，密码长度必须大于{min}，小于{max}")
    private String password;

    private String roleName;
}
