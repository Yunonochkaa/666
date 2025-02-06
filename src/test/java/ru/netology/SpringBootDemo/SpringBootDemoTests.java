package ru.netology.SpringBootDemo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootDemoTests {

    @Autowired
    private TestRestTemplate restTemplate;

    static GenericContainer<?> devApp = new GenericContainer<>("devapp").withExposedPorts(8080);

    static GenericContainer<?> prodApp = new GenericContainer<>("prodapp").withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devApp.start();
        prodApp.start();
    }

    @Test
    void testDevApp() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + devApp.getMappedPort(8080), String.class);
        System.out.println(response.getBody());
        assertEquals("Expected Response", response.getBody());
    }

    @Test
    void testProdApp() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + prodApp.getMappedPort(8081), String.class);
        System.out.println(response.getBody());
        assertEquals("Expected Response", response.getBody());
    }
}
