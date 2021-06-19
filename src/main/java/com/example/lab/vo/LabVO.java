package com.example.lab.vo;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LabVO {
    @Size(min = 1, message = "实验室名称不能为空")
    private String name;

    @Size(min = 1, message = "实验室位置不能为空")
    private String location;

    @Min(value = 0, message = "你输入的值为${validatedValue}，实验室最小容纳人数必须大于等于{value}")
    private Integer number;

    private String memo;
}
