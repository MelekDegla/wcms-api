package com.wecode.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wecode.controller.TaskController;
import com.wecode.entity.Project;
import com.wecode.entity.Task;
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


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskControllerIntegrationTest {

    String url = "http://localhost:8091";
    String authAdmin= "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNb250YXNzYXIiLCJzY29wZXMiOiJST0xFX0FETUlOIiwiaWF0IjoxNTc0OTU4MjEyLCJleHAiOjE1NzUwNDQ2MTJ9.UFOjsGTToDnLRJb-G2p7B2YFfEYJBD4k7pW-kEx4eQE";
    String authNoadmin= "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3aWVtIiwic2NvcGVzIjoiUk9MRV9VU0VSIiwiaWF0IjoxNTc0OTU5MTE0LCJleHAiOjE1NzUwNDU1MTR9.bIWf_UIhW1-I3YRlbYEIoVPUt_igYJPN5pkPoUmzgT0";


    @Mock
    TaskController taskController;

    HttpHeaders headersAdmin = new HttpHeaders();
    HttpHeaders headersNoAdmin = new HttpHeaders();
    HttpHeaders headersNoAuth = new HttpHeaders();
    TestRestTemplate restTemplate = new TestRestTemplate();
    private static final ObjectMapper om = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        headersAdmin.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headersAdmin.add("Authorization", "Bearer "+ authAdmin);

        headersNoAdmin.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headersNoAdmin.add("Authorization", "Bearer "+ authNoadmin);
    }

    @Test
    void listTask() {
        //For Admin Test
        HttpEntity<Task[]> entityFromAdmin = new HttpEntity<Task[]>(null, headersAdmin);
        HttpEntity<List<Task>> response = restTemplate.exchange(
                url+"/tasks", HttpMethod.GET, entityFromAdmin, new ParameterizedTypeReference<List<Task>>(){});
        assertNotNull(response.getBody());

        // For No Admin Test
        HttpEntity<Task[]> entityNoAdmin = new HttpEntity<>(null, headersNoAdmin);
        HttpEntity<String> resNoAdmin = restTemplate.exchange(
                url+"/tasks", HttpMethod.GET, entityNoAdmin, String.class);
        assertNotNull(resNoAdmin.getBody());

        // For No Auth Test
        HttpEntity<Task[]> entity = new HttpEntity<>(null, headersNoAuth);
        HttpEntity<String> resNoAuth = restTemplate.exchange(
                url+"/tasks", HttpMethod.GET, entity, String.class);
        assert resNoAuth.getBody().contains("Unauthorized") ;
    }

   @Test
    void getOne() { //Problem Here
       // For Admin Test
       HttpEntity<Task> responseAdmin = restTemplate.exchange(
               url+"/tasks/2", HttpMethod.GET, new HttpEntity<Task>(null, headersAdmin), Task.class);

       assertNotNull(responseAdmin.getBody());
       assertEquals("Task2", responseAdmin.getBody().getDescription());

       // For No Admin Test
       HttpEntity<Task> responseNoAdmin = restTemplate.exchange(
               url+"/tasks/1", HttpMethod.GET, new HttpEntity<Task>(null, headersNoAdmin), Task.class);

       assertNotNull(responseNoAdmin.getBody());
       assertEquals("Task2", responseAdmin.getBody().getDescription());

       // For No Auth Test
       HttpEntity<String> responseNoAuth = restTemplate.exchange(
               url+"/tasks/1", HttpMethod.GET, new HttpEntity<String>(null, headersNoAuth), String.class);

       assertNotNull(responseNoAuth.getBody());
       assert responseNoAuth.getBody().contains("Unauthorized") ;
    }


    @Test
    void saveTask() {
        Task task =new Task();
        task.setDescription("INTTest");
        task.setLabel("karim");
        task.setStatus(1);
        task.setProject(new Project());


        // For Admin Test
        HttpEntity<Task> entityAdmin = new HttpEntity<Task>(task, headersAdmin);
        restTemplate.postForEntity(url+"/tasks", entityAdmin, String.class);


        HttpEntity<Task> entityFromAdmin = new HttpEntity<Task>(null, headersAdmin);
        ResponseEntity<Task> resAdmin = restTemplate.exchange(
                url+"/tasks/description/"+task.getDescription(), HttpMethod.GET, entityFromAdmin, Task.class);

        assertEquals(task.getDescription(), resAdmin.getBody().getDescription());
        assertEquals(task.getStatus(), resAdmin.getBody().getStatus());
        assertEquals(task.getLabel(), resAdmin.getBody().getLabel());

        // For No Admin Test
        Task taskNoAdmin =new Task();
        taskNoAdmin.setDescription("NOfADMIN");
        taskNoAdmin.setLabel("NOADMIdN");
        taskNoAdmin.setStatus(1);
        taskNoAdmin.setProject(new Project());

        HttpEntity<Task> entityNoAdmin = new HttpEntity<Task>(taskNoAdmin, headersNoAdmin);
        restTemplate.exchange(url+"/tasks", HttpMethod.POST, entityNoAdmin, String.class);


        ResponseEntity<Task> resNoAdmin = restTemplate.exchange(
                url+"/tasks/description/"+taskNoAdmin.getDescription(), HttpMethod.GET, entityNoAdmin, Task.class);
        assertEquals(taskNoAdmin.getDescription(), resNoAdmin.getBody().getDescription());
        assertEquals(taskNoAdmin.getStatus(), resNoAdmin.getBody().getStatus());
        assertEquals(taskNoAdmin.getLabel(), resNoAdmin.getBody().getLabel());

        // For No Auth Test

        HttpEntity<Task> entityNoAuth = new HttpEntity<Task>(taskNoAdmin, headersNoAuth);
        try {
            HttpEntity<String> responseNoAAuth = restTemplate.exchange(url + "/tasks", HttpMethod.POST, entityNoAuth, String.class);
        }
        catch (Exception e)
        {
            assert e.getMessage().contains("authentication") ;
        }
    }

    @Test
    void delete() {
        // For Admin Test
        Task task =new Task();
        task.setDescription("DELTEST");
        task.setLabel("KARIM");
        task.setStatus(1);
        task.setProject(new Project());

        HttpEntity<Task> entityAdmin = new HttpEntity<Task>(task, headersAdmin);
        restTemplate.postForEntity(url+"/tasks", entityAdmin, String.class);

        HttpEntity<Task> entityFromAdmin = new HttpEntity<Task>(null, headersAdmin);
        ResponseEntity<Task> res = restTemplate.exchange(
                url+"/tasks/description/DELTEST", HttpMethod.GET, entityFromAdmin, Task.class);

        restTemplate.exchange(url+"/tasks/"+res.getBody().getId(), HttpMethod.DELETE, entityFromAdmin, Task.class);

        HttpEntity<Task> res2 = restTemplate.exchange(
                url+"/tasks/description/DELTEST", HttpMethod.GET, entityFromAdmin, Task.class);

        assertNull(res2.getBody().getLabel());


        // For No Admin Test
        Task taskNoAdmin =new Task();
        taskNoAdmin.setDescription("NOADMIN");
        taskNoAdmin.setLabel("KARIM");
        taskNoAdmin.setStatus(1);
        taskNoAdmin.setProject(new Project());

        HttpEntity<Task> entityNoAdmin = new HttpEntity<Task>(taskNoAdmin, headersNoAdmin);
        restTemplate.postForEntity(url+"/tasks", entityNoAdmin, String.class);

        HttpEntity<Task> entityNoAdmin1 = new HttpEntity<Task>(null, headersNoAdmin);
        ResponseEntity<Task> resNoAdmin = restTemplate.exchange(
                url+"/tasks/description/NOADMIN", HttpMethod.GET, entityNoAdmin1, Task.class);

        ResponseEntity<Task> resultNoAdmin= restTemplate.exchange(url+"/tasks/"+resNoAdmin.getBody().getId(), HttpMethod.DELETE, entityNoAdmin1, Task.class);
        assert resultNoAdmin.getBody() == null;

        // No Auth Test
        HttpEntity<Task> entityNoAuth = new HttpEntity<Task>(null, headersNoAuth);
        ResponseEntity<String> resultNoAuth= restTemplate.exchange(url+"/tasks/3", HttpMethod.DELETE, entityNoAuth, String.class);
        assert resultNoAuth.getBody().contains("Unauthorized");
    }

}