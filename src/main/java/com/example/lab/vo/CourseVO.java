package com.example.lab.vo;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CourseVO {
    @Size(min = 1, message = "课程名称不能为空")
    private String name;

    @Min(value = 1, message = "课程学生人数必须大于0")
    private Integer studentAmount;

    @NotNull
    private Long labId;

    private String memo;
}
