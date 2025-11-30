package org.example.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/readiness")
public class CustomReadinessHealthIndicator extends AbstractHealthIndicator {
    private volatile Status status = Status.UP;

    @PostMapping
    public void health(@RequestParam Status status) {
        log.info("Change readiness status to: {}", status);
        this.status = status;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.status(this.status).withDetail("custom", "details");
    }
}
