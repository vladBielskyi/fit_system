package com.library.commons;

import com.library.commons.enums.TrainingStatus;
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
public class TrainingDto {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private TrainingStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private WorkoutDto workout;
    private UserDto user;
}
