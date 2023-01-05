package com.training.web.rest;

import com.training.service.TaskService;
import com.training.service.dto.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskResource {
    private final Logger logger = LoggerFactory.getLogger(TaskResource.class);

    private final TaskService taskService;

    public TaskResource(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks(
        @RequestParam(value = "search", required = false) String search,
        @RequestParam(value = "deadline", required = false) LocalDateTime deadline,
        @RequestParam(value = "state", required = false) String state,
        @PageableDefault Pageable pageable
    ) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskDetails(@PathVariable("id") Long id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<Object> createTask(@RequestBody TaskDTO taskDTO) {
        return  null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@RequestBody TaskDTO taskDTO) {
        return null;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> changeState(@PathVariable("id") Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable("id") Long id) {
        return null;
    }
}
