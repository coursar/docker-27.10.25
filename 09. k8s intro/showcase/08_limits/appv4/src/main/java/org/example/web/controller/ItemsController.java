package org.example.web.controller;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemsController {
    private final JdbcClient jdbc;

    public ItemsController(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping
    public List<ItemDto> items() {
        return jdbc.sql("SELECT id, name FROM items LIMIT 10")
                .query(ItemDto.class)
                .list()
                ;
    }

    public record ItemDto(long id, String name) {}
}
