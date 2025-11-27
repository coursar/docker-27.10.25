package org.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.bodyconverter.BodyConverter;
import org.exception.ConversionException;
import org.http.ContentTypes;
import org.service.UserService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final List<BodyConverter> converters;

    public void getAll(HttpServletRequest request, HttpServletResponse response) {
        final var data = service.getAll();
        write(data, ContentTypes.APPLICATION_JSON, response);
    }

    public void write(Object data, String contentType, HttpServletResponse response) {
        for (final var converter : converters) {
            if (!converter.canWrite(contentType, data.getClass())) {
                continue;
            }

            try {
                response.setContentType(contentType);
                converter.write(response.getWriter(), data);
                return;
            } catch (IOException e) {
                throw new ConversionException(e);
            }
        }

        throw new ConversionException("no converters support given content type");
    }
}
