package com.example.lab.entity.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CourseDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String name;

    private Integer studentAmount;

    private String labName;

    private String memo;
}
