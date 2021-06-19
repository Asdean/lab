package com.example.lab.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CourseRecordVO {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long courseId;

    @Min(value = 1, message = "课程开始周数必须大于0")
    @Max(value = 18, message = "课程开始周数必须小于18")
    private Integer weekStartNo;

    @Min(value = 1, message = "课程持续周数必须大于0")
    @Max(value = 18, message = "课程持续周数必须小于0")
    private Integer weekKeep;

    private Integer sectionNo;

    private Integer weekday;
}
