package com.training.repository;

import com.training.domain.Task;
import com.training.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	Page<Task> findAllByAuthor(User currentUser, Pageable pageable);

    Page<Task> findAllByAuthorAndDeadline(User currentUser, Instant deadline, Pageable pageable);

    Page<Task> findAllByAuthorAndState(User currentUser, String state, Pageable pageable);

    Page<Task> findAllByAuthorAndDeadlineAndState(User currentUser, Instant deadline, String state, Pageable pageable);

    Optional<Task> findByIdAndAuthor(Long id, User currentUser);
}
