package test;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskControllerIntegrationTest {

    @Mock
    TaskController taskController;

    HttpHeaders headers = new HttpHeaders();
    TestRestTemplate restTemplate = new TestRestTemplate();
    private static final ObjectMapper om = new ObjectMapper();

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void listTask() {
        HttpEntity<Task[]> entity = new HttpEntity<Task[]>(null, headers);
        ResponseEntity<List<Task>> response = restTemplate.exchange(
                "http://localhost:8090/tasks", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Task>>(){});

        assertNotNull(response.getBody());
    }

    @Test
    void getOne() { //Problem Here
        ResponseEntity<Task> response = restTemplate.exchange(
                "http://localhost:8090/tasks/1", HttpMethod.GET, new HttpEntity<Task>(null, headers), Task.class);

        assertNotNull(response.getBody());
        assertEquals("Task 1", response.getBody().getLabel());
    }

    @Test
    void saveTask() {
        Task task =new Task();
        task.setDescription("INTTest");
        task.setLabel("karim");
        task.setStatus(1);
        task.setProject(new Project());


       restTemplate.postForEntity("http://localhost:8090/tasks", task, String.class);


        ResponseEntity<Task> res = restTemplate.exchange(
                "http://localhost:8090/tasks/description/"+task.getDescription(), HttpMethod.GET, new HttpEntity<Task>(null, headers), Task.class);

        assertEquals("karim", res.getBody().getLabel());
    }

    @Test
    void update() { //Problem Here
        Task task =new Task();
        task.setDescription("UpdateITEST");
        task.setLabel("karimTest");
        task.setStatus(0);
        task.setProject(new Project());

        restTemplate.postForEntity("http://localhost:8090/tasks", task, String.class);

        ResponseEntity<Task> res = restTemplate.exchange(
                "http://localhost:8090/tasks/description/"+task.getDescription(), HttpMethod.GET, new HttpEntity<Task>(null, headers), Task.class);

        task.setId(res.getBody().getId());
        task.setLabel("UpdatedS");
        task.setStatus(33);

       restTemplate.put("http://localhost:8090/tasks", task, String.class);

        ResponseEntity<Task> rst = restTemplate.exchange(
                "http://localhost:8090/tasks/"+task.getId(), HttpMethod.GET, new HttpEntity<Task>(null, headers), Task.class);

        assertEquals(task.getLabel(), rst.getBody().getLabel());
        assertEquals(task.getStatus(), rst.getBody().getStatus());
    }

    @Test
    void delete() {
        Task task =new Task();
        task.setDescription("DelTest");
        task.setLabel("karimTest");
        task.setStatus(0);
        task.setProject(new Project());

        restTemplate.postForEntity("http://localhost:8090/tasks", task, String.class);



        HttpEntity<Task> entity = new HttpEntity<Task>(null, headers);
        ResponseEntity<Task> res = restTemplate.exchange(
                "http://localhost:8090/tasks/description/"+task.getDescription(), HttpMethod.GET, entity, Task.class);

        restTemplate.exchange("http://localhost:8090/tasks/"+res.getBody().getId(), HttpMethod.DELETE, entity, Task.class);

        ResponseEntity<Task> res2 = restTemplate.exchange(
                "http://localhost:8090/tasks/description/"+task.getDescription(), HttpMethod.GET, entity, Task.class);

        assertNull(res2.getBody().getLabel());
    }
}