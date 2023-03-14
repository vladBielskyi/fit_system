package com.library.commons;

import com.library.commons.enums.ExerciseType;
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
public class ExerciseDto {
    private Long id;
    private ExerciseType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private WorkoutDto workout;
}
