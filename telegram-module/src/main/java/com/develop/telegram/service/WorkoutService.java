package com.develop.telegram.service;

import com.library.commons.WorkoutDto;

import java.util.List;

public interface WorkoutService {

    public List<WorkoutDto> getAllWorkoutsByUserId(Long userId);

    public List<String> getWorkoutNamesByUserId(Long userId);
}
