package com.wecode;

import com.wecode.controller.UserController;
import com.wecode.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserControllerIntegrationTest {

    String url = "http://localhost:8091";
    String authAdmin = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNb250YXNzYXIiLCJzY29wZXMiOiJST0xFX0FETUlOIiwiaWF0IjoxNTc1MTk4OTA5LCJleHAiOjE1NzUyODUzMDl9.ZJZ4zygIFKMdh4vZR_PlKkuQsgksyjf-9m1Cktfp3bM";
    String authNoadmin = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3aWVtIiwic2NvcGVzIjoiUk9MRV9VU0VSIiwiaWF0IjoxNTc1MTk4OTQ0LCJleHAiOjE1NzUyODUzNDR9.JUby7uAl93pftDPpXGAh6zwmj_fXyi9vrsFQvUg8unA";



    @Mock
    UserController userController;

    HttpHeaders headersAdmin = new HttpHeaders();
    HttpHeaders headersNoAdmin = new HttpHeaders();
    HttpHeaders headersNoAuth = new HttpHeaders();

    TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        headersAdmin.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headersAdmin.add("Authorization", "Bearer " + authAdmin);

        headersNoAdmin.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headersNoAdmin.add("Authorization", "Bearer " + authNoadmin);
    }

    @Test
    void listUser() {

        //For Admin Test
        HttpEntity<User[]> entityFromAdmin = new HttpEntity<User[]>(null, headersAdmin);
        HttpEntity<List<User>> response = restTemplate.exchange(
                url + "/users", HttpMethod.GET, entityFromAdmin, new ParameterizedTypeReference<List<User>>() {
                });
        assertNotNull(response.getBody());

        // For No Admin Test
        HttpEntity<User[]> entityNoAdmin = new HttpEntity<>(null, headersNoAdmin);
        HttpEntity<String> resNoAdmin = restTemplate.exchange(
                url + "/users", HttpMethod.GET, entityNoAdmin, String.class);
        System.out.println(resNoAdmin.getBody());
        assert resNoAdmin.getBody().contains("Forbidden");

        // For No Auth Test
        HttpEntity<User[]> entity = new HttpEntity<>(null, headersNoAuth);
        HttpEntity<String> resNoAuth = restTemplate.exchange(
                url + "/users", HttpMethod.GET, entity, String.class);
        assert resNoAuth.getBody().contains("Unauthorized");
    }

    @Test
    void getOne() {
        // For Admin Test
        HttpEntity<User> responseAdmin = restTemplate.exchange(
                url + "/users/1", HttpMethod.GET, new HttpEntity<User>(null, headersAdmin), User.class);

        assertNotNull(responseAdmin.getBody());
        assertEquals("Degla", responseAdmin.getBody().getUsername());

        // For No Admin Test  //
        HttpEntity<User> responseNoAdmin = restTemplate.exchange(
                url + "/users/1", HttpMethod.GET, new HttpEntity<User>(null, headersNoAdmin), User.class);

        assertNotNull(responseNoAdmin.getBody());
        assertEquals("Degla", responseAdmin.getBody().getUsername());

        // For No Auth Test
        HttpEntity<String> responseNoAuth = restTemplate.exchange(
                url + "/users/1", HttpMethod.GET, new HttpEntity<String>(null, headersNoAuth), String.class);

        assertNotNull(responseNoAuth.getBody());
        assert responseNoAuth.getBody().contains("Unauthorized");
    }


    @Test
    void saveUser() { //ADMIN And No Admin Problem

        // For Admin Test
        User user = new User();
        user.setUsername("KARIMTEST");
        user.setPassword("abc123");
        user.setBirthdate("1998-01-20");
        user.setCin("1505093df08");
        user.setEmail("kafrfim@gmail.com");



        HttpEntity<User> entityAdmin = new HttpEntity<User>(user, headersAdmin);
        restTemplate.postForEntity(url+"/users", entityAdmin, String.class);



        HttpEntity<User> entityFromAdmin = new HttpEntity<User>(null, headersAdmin);
        ResponseEntity<User> resAdmin = restTemplate.exchange(
                url+"/users/username/"+user.getUsername(), HttpMethod.GET, entityFromAdmin, User.class);

        assertEquals(user.getUsername(), resAdmin.getBody().getUsername());
        assertEquals(user.getCin(), resAdmin.getBody().getCin());
        assertEquals(user.getEmail(), resAdmin.getBody().getEmail());
        assertEquals(user.getLeaveBalance(), resAdmin.getBody().getLeaveBalance());
        assertEquals(user.getAddress(), resAdmin.getBody().getAddress());

        // For No Admin Test
        User userNoAdmin = new User();
        userNoAdmin.setUsername("noadmin");
        userNoAdmin.setPassword("123456");
        userNoAdmin.setBirthdate("1998-01-20");
        userNoAdmin.setSalary(1000);
        userNoAdmin.setCin("15009308");
        userNoAdmin.setEmail("karim@gmail.com");
        userNoAdmin.setLeaveBalance(0L);

        HttpEntity<User> entityNoAdmin = new HttpEntity<User>(userNoAdmin, headersNoAdmin);
        restTemplate.exchange(url+"/users", HttpMethod.POST, entityNoAdmin, String.class);


        ResponseEntity<User> resNoAdmin = restTemplate.exchange(
                url+"/users/username/"+userNoAdmin.getUsername(), HttpMethod.GET, entityNoAdmin, User.class);

        assertEquals(userNoAdmin.getUsername(), resNoAdmin.getBody().getUsername());
        assertEquals(userNoAdmin.getCin(), resNoAdmin.getBody().getCin());
        assertEquals(userNoAdmin.getEmail(), resNoAdmin.getBody().getEmail());
        assertEquals(userNoAdmin.getLeaveBalance(), resNoAdmin.getBody().getLeaveBalance());
        assertEquals(userNoAdmin.getAddress(), resNoAdmin.getBody().getAddress());

        // For No Auth Test

        HttpEntity<User> entityNoAuth = new HttpEntity<User>(userNoAdmin, headersNoAuth);
        try {
            HttpEntity<String> responseNoAAuth = restTemplate.exchange(url + "/users", HttpMethod.POST, entityNoAuth, String.class);
        }
        catch (Exception e)
        {
            assert e.getMessage().contains("authentication") ;
        }

    }


}
