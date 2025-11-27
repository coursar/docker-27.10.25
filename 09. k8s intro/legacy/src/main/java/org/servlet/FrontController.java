package org.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bodyconverter.GsonBodyConverter;
import org.controller.UserController;
import org.exception.InitializationException;
import org.http.Handler;
import org.repository.UserRepository;
import org.service.UserService;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FrontController extends HttpServlet {
    private final Handler notFoundHandler = (request, response) -> response.sendError(404, "Page not found");
    private Map<String, Map<String, Handler>> routes;

    @Override
    public void init() {
        try {
            final var cxt = new InitialContext();
            final var ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/db");
            final var repository = new UserRepository(ds);
            final var service = new UserService(repository);
            final var controller = new UserController(
                    service,
                    List.of(new GsonBodyConverter(new Gson()))
            );
            // TODO: builder
            routes = Map.of(
                    "GET", Map.of(
                            "/api/getUsers", controller::getAll
                    )
            );

            repository.init();
        } catch (Exception e) {
            throw new InitializationException(e);
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        final var path = request.getServletPath(); // FIXME: RTFM
        final var method = request.getMethod();

        final var handler = Optional.ofNullable(routes.get(method))
                .map(o -> o.get(path))
                .orElseGet(() -> notFoundHandler);

        try {
            handler.handle(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
