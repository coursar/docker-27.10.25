package sidecar;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class Sidecar {
    private static final Logger log = Logger.getLogger(Sidecar.class.getName());

    public static void main(String[] args) {
        log.info("Sidecar container Started");

        try {
            final ServerSocket serverSocket = new ServerSocket(9999);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                log.info("Sidecar container stopped");
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream())
                ) {
                    log.info("connection received");
                    out.write((
                            "HTTP/1.1 200 OK\r\n"
                                    + "Content-Length: 0\r\n"
                                    + "Connection: close\r\n"
                                    + "\r\n")
                            .getBytes(StandardCharsets.UTF_8));
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
