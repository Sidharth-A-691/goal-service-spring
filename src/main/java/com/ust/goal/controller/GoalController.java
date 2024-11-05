package com.ust.goal.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ust.goal.dto.GoalDto;
import com.ust.goal.model.Goal;
import com.ust.goal.service.GoalService;

@RestController
@RequestMapping("/goals")
@CrossOrigin(origins = "http://localhost:4200")
public class GoalController {

    private final GoalService goalService;
    private static final Logger logger = LoggerFactory.getLogger(GoalController.class);

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping("/create")
    public ResponseEntity<Goal> createGoal(@RequestBody GoalDto goalDto) {
        Goal goal = goalService.createGoal(goalDto);
        return ResponseEntity.ok(goal);
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<String>> getGoalNotifications() {
        List<String> notifications = goalService.getGoalNotifications();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Goal>> getAllGoals() {
        try {
            List<Goal> goals = goalService.getAllGoals();
            return ResponseEntity.ok(goals);
        } catch (Exception e) {
            // Log the exception and return a 500 response if an error occurs
            logger.error("An unexpected error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Goal>> getAllGoals(@PathVariable Long userId) {
        try {
            List<Goal> goals = goalService.getAllGoalsForUser(userId);
            if (goals.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(goals);
            }       
            return ResponseEntity.ok(goals);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while fetching goals for user " + userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


//    @GetMapping("/all")
//    public ResponseEntity<List<Goal>> getAllGoals() {
//        List<Goal> goals = goalService.getAllGoals();
//        return ResponseEntity.ok(goals);
//    }


    @GetMapping("/{id}")
    public ResponseEntity<Goal> getGoalById(@PathVariable Long id) {
        Goal goal = goalService.getGoalById(id);
        return ResponseEntity.ok(goal);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }
}
