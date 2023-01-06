package com.training.web.rest;

import com.training.IntegrationTest;
import com.training.domain.Task;
import com.training.domain.User;
import com.training.repository.TaskRepository;
import com.training.repository.UserRepository;
import com.training.security.AuthoritiesConstants;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Integration tests for the {@link TaskResource} REST controller
 */
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
@IntegrationTest
public class TaskResourceIT {

    private static final String DEFAULT_TITLE = "Title";
    private static final String UPDATED_TITLE = "Updated Title";

    private static final String DEFAULT_CONTENT = "Testing content";
    private static final String UPDATED_CONTENT = "Result content";

    private static final User DEFAULT_AUTHOR = new User();

    private static final String DEFAULT_STATE = "Pending";
    private static final String UPDATED_STATE = "Doing";

    private static final Instant DEFAULT_DEADLINE = Instant.now();
    private static final Instant UPDATED_DEADLINE = Instant.now().plus(1, ChronoUnit.DAYS);


    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserMockMvc;

    private Task task;

    /**
     * Create a Task.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which has a required relationship to the Task entity.
     */
    public static Task createEntity(EntityManager em) {
        Task task = new Task();
        task.setTitle(DEFAULT_TITLE + RandomStringUtils.randomAlphanumeric(10));
        DEFAULT_AUTHOR.setId(1L);
        DEFAULT_AUTHOR.setLogin("admin");
        task.setAuthor(DEFAULT_AUTHOR);
        task.setContent(DEFAULT_CONTENT + RandomStringUtils.randomAlphanumeric(10));
        task.setState(DEFAULT_STATE);
        task.setDeadline(DEFAULT_DEADLINE);

        return task;
    }

    /**
     * Setups the database with one task.
     */
    public static Task initTestTask(TaskRepository taskRepository, EntityManager em) {
        taskRepository.deleteAll();
        Task task = createEntity(em);
        task.setTitle(DEFAULT_TITLE);
        task.setContent(DEFAULT_CONTENT);
        return task;
    }

    @BeforeEach
    public void initTest() {
        this.task = initTestTask(this.taskRepository, this.em);
    }

//    Testing section below

}
