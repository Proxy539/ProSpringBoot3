package com.apress.myretro;

import com.apress.myretro.client.User;
import com.apress.myretro.client.UsersClient;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UsersClientTest {

    private static HttpServer usersStubServer;

    @Autowired
    UsersClient usersClient;

    @BeforeAll
    static void startUsersStubServer() throws IOException {
        usersStubServer = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        usersStubServer.createContext("/users/norma@email.com", exchange -> {
            byte[] body = """
                    {"email":"norma@email.com","name":"Norma","userRole":["USER"],"active":true}
                    """.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, body.length);
            exchange.getResponseBody().write(body);
            exchange.close();
        });
        usersStubServer.start();
    }

    @AfterAll
    static void stopUsersStubServer() {
        usersStubServer.stop(0);
    }

    @DynamicPropertySource
    static void usersServiceProperties(DynamicPropertyRegistry registry) {
        registry.add("service.users.port", () -> usersStubServer.getAddress().getPort());
    }

    @Test
    public void findUserTest() {
        User user = usersClient.findUserByEmail("norma@email.com");
        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("Norma");
        assertThat(user.getEmail()).isEqualTo("norma@email.com");
    }
}
