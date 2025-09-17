package com.example.basic_task_tracer.Repositories;

import com.example.basic_task_tracer.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface UserRepository extends JpaRepository<User, Long> {


    boolean existsByUsername(String username);
    Stream<User> streamAllBy();

}
