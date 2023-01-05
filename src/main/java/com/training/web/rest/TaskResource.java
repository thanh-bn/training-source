package com.training.web.rest;

import com.training.domain.Task;
import com.training.domain.User;
import com.training.repository.TaskRepository;
import com.training.service.TaskService;
import com.training.service.dto.AdminUserDTO;
import com.training.service.dto.TaskDTO;
import com.training.web.rest.errors.BadRequestAlertException;
import com.training.web.rest.errors.EmailAlreadyUsedException;
import com.training.web.rest.errors.InvalidTaskIdException;
import com.training.web.rest.errors.LoginAlreadyUsedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskResource {

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Logger logger = LoggerFactory.getLogger(TaskResource.class);

    private final TaskService taskService;
    private final TaskRepository taskRepository;

    public TaskResource(TaskService taskService, TaskRepository taskRepository) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }

    /**
     * {@code GET /tasks} : get all tasks with all the details - calling this are only allowed for the administrators.
     *
     * @param search keyword to search for title or content
     * @param deadline deadline to find
     * @param state single state to find
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all tasks.
     */
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks(
        @RequestParam(value = "search", required = false) String search,
        @RequestParam(value = "deadline", required = false) LocalDateTime deadline,
        @RequestParam(value = "state", required = false) String state,
        @PageableDefault Pageable pageable
    ) {
        return null;
    }

    /**
     * {@code GET /tasks/:id} : get the task details.
     *
     * @param id the id of the task to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the task details, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskDetails(@PathVariable("id") Long id) {
        return null;
    }

    /**
     * {@code POST  /tasks}  : Creates a new task.
     * <p>
     * Creates a new task
     *
     * @param taskDTO the task to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new task, or with status {@code 400 (Bad Request)} if fields constraints are not satisfied.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException {@code 400 (Bad Request)} if fields constraints are not satisfied.
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskDTO taskDTO) throws URISyntaxException {
        logger.debug("REST request to save Task : {}", taskDTO);

        if (taskDTO.getId() != null) {
            throw new BadRequestAlertException("A new task cannot already have an ID", "taskManagement", "idexists");
            // Lowercase the user login before comparing with database
        }  else {
            Task newTask = taskService.createTask(taskDTO);
            return ResponseEntity
                .created(new URI("/api/tasks/" + newTask.getId()))
                .headers(
                    HeaderUtil.createAlert(applicationName, "A task is created with identifier " + newTask.getId(), "" + newTask.getId())
                )
                .body(newTask);
        }
    }

    /**
     * {@code PUT /tasks/:id} : Updates an existing task.
     *
     * @param taskDTO the task to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated task.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@Valid @RequestBody TaskDTO taskDTO, @PathVariable("id") Long id) {
        logger.debug("REST request to update Task : {}", taskDTO);
        Optional<Task> existingTask = taskRepository.findById(taskDTO.getId());
        if (existingTask.isPresent() && (!existingTask.get().getId().equals(id))) {
            throw new InvalidTaskIdException();
        }

        Optional<TaskDTO> updatedTask = taskService.updateTask(taskDTO);

        return ResponseUtil.wrapOrNotFound(
            updatedTask,
            HeaderUtil.createAlert(applicationName, "A task is updated with identifier " + taskDTO.getId(), "" + taskDTO.getId())
        );
    }

    /**
     * {@code PATCH /tasks/:id} : Change state an existing task.
     *
     * @param id the task to change state.
     * @param state state to change
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated task.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<TaskDTO> changeState(@PathVariable("id") Long id, @RequestBody String state) {
        logger.debug("REST request to change state of Task : {}", state);

        Optional<TaskDTO> updatedTask = taskService.changeState(id, state);

        return ResponseUtil.wrapOrNotFound(
            updatedTask,
            HeaderUtil.createAlert(applicationName, "A task is updated with identifier " + id, "" + id)
        );
    }

    /**
     * {@code DELETE /tasks/:id} : delete the task.
     *
     * @param id the id of the task to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable("id") Long id) {
        return null;
    }
}
