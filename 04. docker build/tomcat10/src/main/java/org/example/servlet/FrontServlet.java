package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.List;

public class FrontServlet extends HttpServlet {
  private final Gson gson = new Gson();

  @SneakyThrows
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String serverInfo = getServletContext().getServerInfo();

    MessageDigest instance = MessageDigest.getInstance("SHA3-256");
    byte[] digest = instance.digest(serverInfo.getBytes(StandardCharsets.UTF_8));
    String hexString = HexFormat.of().formatHex(digest);

    resp.setHeader("Content-Type", "application/json");
    this.gson.toJson(ItemDto.builder().id(1L).name("server").value(hexString), resp.getWriter());
  }


  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  @With
  public static class ItemDto {
    private long id;
    private String name;
    private String value;
  }
}
