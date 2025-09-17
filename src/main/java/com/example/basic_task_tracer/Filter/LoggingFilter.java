package com.example.basic_task_tracer.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("Incoming request: {} {}", request.getMethod(), request.getRequestURI());

        filterChain.doFilter(request, response);

        log.info("Response status: {} for {} {}",
                response.getStatus(), request.getMethod(), request.getRequestURI());
    }
}

