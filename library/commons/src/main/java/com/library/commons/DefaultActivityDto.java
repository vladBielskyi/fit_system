package com.library.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DefaultActivityDto {
    private Long id;
    private Integer rep;
    private Integer weight;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ExerciseDto exercise;
    private TrainingDto training;
}
