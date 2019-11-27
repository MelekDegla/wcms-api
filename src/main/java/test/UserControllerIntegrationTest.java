package test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wecode.controller.UserController;
import com.wecode.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerIntegrationTest {

    @Mock
    UserController userController;

    HttpHeaders headers = new HttpHeaders();
    TestRestTemplate restTemplate = new TestRestTemplate();
    private static final ObjectMapper om = new ObjectMapper();

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void listUser() {

        ResponseEntity<List<User>> response = restTemplate.exchange(
                "http://localhost:8090/users", HttpMethod.GET, new HttpEntity<User[]>(null, headers), new ParameterizedTypeReference<List<User>>(){});

        assertNotNull(response.getBody());
    }

    @Test
    void getOne() {

        ResponseEntity<?> response = restTemplate.exchange(
                "http://localhost:8090/users/2", HttpMethod.GET, new HttpEntity<String>(null, headers), String.class);

        assertNotNull(response.getBody());
        assert response.getBody().toString().contains("Unauthorized");
    }

    @Test
    void saveUser() {

        User user = new User();
        user.setUsername("We Code");
        user.setPassword("123456");
        user.setBirthdate(LocalDate.of(1998, 1, 20));
        user.setSalary(1000);
        user.setCin("15009308");
        user.setEmail("karim@gmail.com");
        user.setLeaveBalance(0L);

        restTemplate.postForEntity("http://localhost:8090/users", user, String.class);




        HttpEntity<?> entity = new HttpEntity<User>(null, headers);
        ResponseEntity<?> res = restTemplate.exchange(
                "http://localhost:8090/users/username/"+user.getUsername(), HttpMethod.GET, entity, String.class);

        System.out.println(res.getBody());
        assertNotNull(res.getBody());
        assert res.getBody().toString().contains("Unauthorized");

    }
}