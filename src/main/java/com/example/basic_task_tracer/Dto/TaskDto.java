package com.example.basic_task_tracer.Dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)

public class TaskDto {


    private Long id;

    private String title;
    private String description;
    private Boolean completed;
    private String maskedNumber;




}
