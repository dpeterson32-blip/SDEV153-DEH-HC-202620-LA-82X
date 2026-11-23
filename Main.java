//Dustin Peterson
// 11/22/2026

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.file.Files;

public class Main {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8080), 0);

        server.createContext("/", e ->
                sendPage(e, "pages/index.html"));

        server.createContext("/games", e ->
                sendPage(e, "pages/games.html"));

        server.createContext("/news", e ->
                sendPage(e, "pages/news.html"));

        server.createContext("/reviews", e ->
                sendPage(e, "pages/reviews.html"));

        server.createContext("/contact", e ->
                sendPage(e, "pages/contact.html"));

        server.start();

        System.out.println("Game Site Started!");
        System.out.println("Opening browser...");

        Desktop.getDesktop().browse(
                new URI("http://localhost:8080"));

        System.out.println("Visit: http://localhost:8080");
    }

    private static void sendPage(
            HttpExchange exchange,
            String filePath) throws IOException {

        File file = new File(filePath);

        byte[] data = Files.readAllBytes(
                file.toPath());

        exchange.getResponseHeaders()
                .add("Content-Type", "text/html");

        exchange.sendResponseHeaders(
                200,
                data.length);

        OutputStream os =
                exchange.getResponseBody();

        os.write(data);
        os.close();
    }
}