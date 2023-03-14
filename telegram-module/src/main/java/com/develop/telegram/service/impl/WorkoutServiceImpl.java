package com.develop.telegram.service.impl;

import com.develop.telegram.service.WorkoutService;
import com.library.commons.WorkoutDto;
import com.library.persistence.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<WorkoutDto> getAllWorkoutsByUserId(Long userId) {
        return workoutRepository.findAllById(List.of(userId))
                .stream()
                .map(x -> modelMapper.map(x, WorkoutDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getWorkoutNamesByUserId(Long userId) {
        return workoutRepository.findWorkoutNamesByUserId(userId);
    }
}
