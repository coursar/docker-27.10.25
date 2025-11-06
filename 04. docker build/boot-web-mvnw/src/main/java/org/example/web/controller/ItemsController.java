package org.example.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemsController {
    private final List<ItemDto> items = List.of(
            new ItemDto(1L, "first"),
            new ItemDto(2L, "second")
    );

    @GetMapping
    public List<ItemDto> items() {
        return this.items;
    }

    public record ItemDto(long id, String name) {}
}
