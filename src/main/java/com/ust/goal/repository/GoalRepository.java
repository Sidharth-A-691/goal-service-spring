package com.ust.goal.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ust.goal.model.Goal;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
	
	List<Goal> findByUserId(Long userId);
}
