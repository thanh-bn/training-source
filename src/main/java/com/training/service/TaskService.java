package com.training.service;

import com.training.domain.Task;
import com.training.domain.User;
import com.training.repository.TaskRepository;
import com.training.repository.UserRepository;
import com.training.security.SecurityUtils;
import com.training.service.dto.TaskDTO;
import com.training.web.rest.errors.LoginNotFound;
import com.training.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Optional;

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

    @Transactional(readOnly = true)
    public Page<TaskDTO> getAllCreatedTask(LocalDateTime deadline, String state, Pageable pageable) {
        User currentUser = this.userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElseThrow(LoginNotFound::new)).orElse(new User());
        int i = (deadline != null ? 1 : 0) | (state != null ? 2 : 0);

        switch (i) {
            case 0:
                return this.taskRepository.findAllByAuthor(currentUser, pageable).map(TaskDTO::new);
            case 1:
                return this.taskRepository.findAllByAuthorAndDeadline(currentUser, Objects.requireNonNull(deadline).toInstant(ZoneOffset.UTC), pageable).map(TaskDTO::new);
            case 2:
                return this.taskRepository.findAllByAuthorAndState(currentUser, Objects.requireNonNull(state), pageable).map(TaskDTO::new);
            case 3:
                return this.taskRepository.findAllByAuthorAndDeadlineAndState(currentUser, Objects.requireNonNull(deadline).toInstant(ZoneOffset.UTC), state, pageable).map(TaskDTO::new);
            default:
                return null;
        }
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

    @Transactional(readOnly = true)
    public Optional<Task> getOneTaskById(Long id) {
        User currentUser = this.userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElseThrow(LoginNotFound::new)).orElse(new User());
        return this.taskRepository.findByIdAndAuthor(id, currentUser);
    }

    public void deleteTask(Long id) {
        User currentUser = this.userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElseThrow(LoginNotFound::new)).orElse(new User());
        this.taskRepository.findByIdAndAuthor(id, currentUser).ifPresent(task -> {
            this.taskRepository.delete(task);
            logger.debug("Deleted task with id: {}", id);
        });
    }
}
