package org.example.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/system")
@RestController
public class SystemController {
    @GetMapping
    public ResponseDto info() {
        return new ResponseDto(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().maxMemory()
        );
    }

    public record ResponseDto(
            int processorCount,
            long maxMemory
    ) {}
}
