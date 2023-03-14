package com.library.persistence;

import com.library.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    @Query(value = "SELECT name FROM workouts WHERE user_id = ?1", nativeQuery = true)
    List<String> findWorkoutNamesByUserId(Long userId);
}
