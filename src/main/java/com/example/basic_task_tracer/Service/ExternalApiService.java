package com.example.basic_task_tracer.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiService {

    private static final Logger log = LoggerFactory.getLogger(ExternalApiService.class);

    private final RestTemplate restTemplate;

    public ExternalApiService() {
        this.restTemplate = new RestTemplate();
    }

    public void fetchAndLogObjects() {
        String url = "https://api.restful-api.dev/objects";
        try {
            String response = restTemplate.getForObject(url, String.class);
            log.info("Response from external API: {}", response);
        } catch (Exception e) {
            log.error("Failed to fetch data from external API", e);
        }
    }
}


