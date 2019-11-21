package test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wecode.controller.ProjectController;
import com.wecode.entity.Project;
import com.wecode.entity.Task;
import com.wecode.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectControllerIntegrationTest {


    @Mock
    ProjectController projectController;

    HttpHeaders headers = new HttpHeaders();
    TestRestTemplate restTemplate = new TestRestTemplate();
    private static final ObjectMapper om = new ObjectMapper();

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void listProject() {
        List<Project> list = projectController.listProject();
        System.out.println(list);
        assertNotNull(list);

    }

    @Test
    void getOne() {
        HttpEntity<Project> entity = new HttpEntity<Project>(null, headers);
        ResponseEntity<Project> response = restTemplate.exchange(
                "http://localhost:8090/projects/1", HttpMethod.GET, entity, Project.class);


        assertEquals("Project 1", response.getBody().getName());
    }

    @Test
    void saveProject() throws JsonProcessingException {
        Project project;
        project = new Project();
        project.setDescription("Projet Test");
        project.setName("Test");
        project.setTasks(new ArrayList<Task>());
        project.setUsers(new ArrayList<User>());

        String expected = om.writeValueAsString(project);

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8090/projects", project, String.class);




        HttpEntity<Project> entity = new HttpEntity<Project>(null, headers);
        ResponseEntity<Project> res = restTemplate.exchange(
                "http://localhost:8090/projects/name/Test", HttpMethod.GET, entity, Project.class);


        assertEquals("Test", res.getBody().getName());



    }

    @Test
    void delete() throws JsonProcessingException {

        Project project;
        project = new Project();
        project.setDescription("Projet Test2");
        project.setName("Delete");
        project.setTasks(new ArrayList<Task>());
        project.setUsers(new ArrayList<User>());

        String expected = om.writeValueAsString(project);

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8090/projects", project, String.class);



        HttpEntity<Project> entity = new HttpEntity<Project>(null, headers);
        ResponseEntity<Project> res = restTemplate.exchange(
                "http://localhost:8090/projects/name/Delete", HttpMethod.GET, entity, Project.class);

        restTemplate.exchange("http://localhost:8090/projects/"+res.getBody().getId(), HttpMethod.DELETE, entity, Project.class);

        ResponseEntity<Project> res2 = restTemplate.exchange(
                "http://localhost:8090/projects/name/Delete", HttpMethod.GET, entity, Project.class);

        assertNull(res2.getBody().getName());

    }

    @Test
    void update() throws JsonProcessingException {

        Project project;
        project = new Project();
        project.setDescription("Projet Test");
        project.setName("Update");
        project.setTasks(new ArrayList<Task>());
        project.setUsers(new ArrayList<User>());

        String expected = om.writeValueAsString(project);

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8090/projects", project, String.class);




        HttpEntity<Project> entity = new HttpEntity<Project>(null, headers);
        ResponseEntity<Project> res = restTemplate.exchange(
                "http://localhost:8090/projects/name/Update", HttpMethod.GET, entity, Project.class);

        project.setId(res.getBody().getId());
        project.setName("Updated");




        restTemplate.put("http://localhost:8090/projects", project, String.class);

        HttpEntity<Project> en = new HttpEntity<Project>(null, headers);
        ResponseEntity<Project> rs = restTemplate.exchange(
                "http://localhost:8090/projects/"+project.getId(), HttpMethod.GET, en, Project.class);








        assertEquals("Updated", rs.getBody().getName());


    }
}