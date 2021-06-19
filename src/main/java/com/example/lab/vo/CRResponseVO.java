package com.example.lab.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CRResponseVO {
    private String courseName;

    private Integer weekNo;

    private Integer sectionNo;

    private Integer weekday;
}
