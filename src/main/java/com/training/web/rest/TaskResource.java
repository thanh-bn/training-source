package com.training.web.rest;

import com.training.service.TaskService;
import com.training.service.dto.TaskDTO;
import com.training.web.rest.errors.BadRequestAlertException;
import com.training.web.rest.errors.EmailAlreadyUsedException;
import com.training.web.rest.errors.LoginAlreadyUsedException;
import com.training.web.rest.vm.TaskStateVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
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
     * @param taskStateVM state to change
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated task.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> changeState(@PathVariable("id") Long id, @RequestBody TaskStateVM taskStateVM) {
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
        return null;
    }
}
