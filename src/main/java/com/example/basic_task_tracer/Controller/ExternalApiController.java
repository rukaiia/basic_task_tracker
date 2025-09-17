package com.example.basic_task_tracer.Controller;

import com.example.basic_task_tracer.Service.ExternalApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExternalApiController {

    private final ExternalApiService externalApiService;

    public ExternalApiController(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    @GetMapping("/api/fetch-objects")
    public String fetchObjects() {
        externalApiService.fetchAndLogObjects();
        return "Request sent, check logs for response";
    }
}


