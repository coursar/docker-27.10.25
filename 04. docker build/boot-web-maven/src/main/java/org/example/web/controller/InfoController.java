package org.example.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.ServletContext;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@RestController
@RequestMapping("/api/info")
public class InfoController {
    private final ServletContext servletContext;

    public InfoController(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @GetMapping
    public ItemDto info() throws NoSuchAlgorithmException {

        String serverInfo = this.servletContext.getServerInfo();

        MessageDigest instance = MessageDigest.getInstance("SHA3-512");
        byte[] digest = instance.digest(serverInfo.getBytes(StandardCharsets.UTF_8));
        String hexString = HexFormat.of().formatHex(digest);

        return new ItemDto(1L, "server", hexString);
    }

    public record ItemDto(long id, String name, String value) {}
}
