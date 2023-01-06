package com.training.web.rest;

import com.training.service.TaskService;
import com.training.service.dto.TaskDTO;
import com.training.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for managing tasks.
 * <p>
 * This class accesses the {@link com.training.domain.Task} entity.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskResource {

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final Logger logger = LoggerFactory.getLogger(TaskResource.class);

    private final TaskService taskService;

    public TaskResource(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * {@code GET /tasks} : get all tasks with all the details - calling this are only allowed for the administrators.
     *
     * @param deadline deadline to find
     * @param state single state to find
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all tasks.
     */
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks(
        @RequestParam(value = "deadline", required = false) LocalDateTime deadline,
        @RequestParam(value = "state", required = false) String state,
        @PageableDefault Pageable pageable
    ) {
        logger.debug("REST request to get all tasks by current account");

        final Page<TaskDTO> page = this.taskService.getAllCreatedTask(deadline, state, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET /tasks/:id} : get the task details.
     *
     * @param id the id of the task to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the task details, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskDetails(@PathVariable("id") Long id) {
        logger.debug("REST request to get task information by id: {}", id);

        return ResponseUtil.wrapOrNotFound(this.taskService.getOneTaskById(id).map(TaskDTO::new));
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
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        return  null;
    }

    /**
     * {@code PUT /tasks/:id} : Updates an existing task.
     *
     * @param taskDTO the task to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated task.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@RequestBody TaskDTO taskDTO) {
        return null;
    }

    /**
     * {@code PATCH /tasks/:id} : Change state an existing task.
     *
     * @param id the task to change state.
     * @param state state to change
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated task.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> changeState(@PathVariable("id") Long id, @RequestBody String state) {
        return null;
    }

    /**
     * {@code DELETE /tasks/:id} : delete the task.
     *
     * @param id the id of the task to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable("id") Long id) {
        logger.debug("REST request to delete task with id: {}", id);

        this.taskService.deleteTask(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createAlert(this.applicationName, "A task is deleted with id: " + id, id.toString()))
            .build();
    }
}
