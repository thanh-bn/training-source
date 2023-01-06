package com.training.service;

import com.training.domain.Authority;
import com.training.domain.Task;
import com.training.repository.TaskRepository;
import com.training.repository.UserRepository;
import com.training.security.SecurityUtils;
import com.training.service.dto.AdminUserDTO;
import com.training.service.dto.TaskDTO;
import com.training.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class TaskService {
    private final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, UserMapper userMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Task createTask(TaskDTO taskDTO) {
        SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> taskDTO.setAuthor(userMapper.userToUserDTO(user)));
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setContent(taskDTO.getContent());
        task.setDeadline(taskDTO.getDeadline());
        task.setState(taskDTO.getState());
        task.setAuthor(userMapper.userFromId(taskDTO.getAuthor().getId()));

        taskRepository.save(task);
        logger.debug("Create information for task: {}", task);
        return task;
    }

    public Optional<TaskDTO> updateTask(TaskDTO taskDTO) {
        return Optional
            .of(taskRepository.findById(taskDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(task -> {
                task.setTitle(taskDTO.getTitle());
                task.setContent(taskDTO.getContent());
                task.setDeadline(taskDTO.getDeadline());
                task.setState(taskDTO.getState());

                taskRepository.save(task);
                logger.debug("Changed Information for Task: {}", task);
                return task;
            })
            .map(TaskDTO::new);
    }

    public Optional<TaskDTO> changeState(Long id, String state) {
        return Optional
            .of(taskRepository.findById(id))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(task -> {
                task.setState(state);
                taskRepository.save(task);
                logger.debug("Changed state for Task: {}", task);
                return task;
            })
            .map(TaskDTO::new);
    }
}
