package com.example.basic_task_tracer.Repositories;


import com.example.basic_task_tracer.Entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Stream<Task> streamAllBy();

    Page<Task> findByMaskedNumberContainingIgnoreCase(String maskedNumber, Pageable pageable);
}
