package com.training.service.taskResource;

import com.training.repository.TaskRepository;
import com.training.repository.UserRepository;
import com.training.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

//    @Nested
//    @DisplayName("Test: TaskService.getAllCreatedTask()")
//    public class TestGetAllCreatedTask {
//        @Test
//        void whenGetAllCreatedTask_shouldReturnList
//    }

}
