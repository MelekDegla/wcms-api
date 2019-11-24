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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
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

        HttpEntity<Project[]> entity = new HttpEntity<Project[]>(null, headers);
        ResponseEntity<List<Project>> response = restTemplate.exchange(
                    "http://localhost:8090/projects", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Project>>(){});

            assertNotNull(response.getBody());
    }

    @Test
    void getOne() {
        ResponseEntity<Project> response = restTemplate.exchange(
                "http://localhost:8090/projects/1", HttpMethod.GET, new HttpEntity<Project>(null, headers), Project.class);

        assertNotNull(response.getBody());
        assertEquals("Project 1", response.getBody().getName());
    }

    @Test
    void saveProject() {
        Project project;
        project = new Project();
        project.setDescription("ProjetITest");
        project.setName("InTest");
        project.setTasks(new ArrayList<Task>());
        project.setUsers(new ArrayList<User>());



       restTemplate.postForEntity("http://localhost:8090/projects", project, String.class);




        HttpEntity<Project> entity = new HttpEntity<Project>(null, headers);
        ResponseEntity<Project> res = restTemplate.exchange(
                "http://localhost:8090/projects/name/"+project.getName(), HttpMethod.GET, entity, Project.class);

        assertEquals(project.getName(), res.getBody().getName());
        assertEquals(project.getUsers(), res.getBody().getUsers());
        assertEquals(project.getTasks(), res.getBody().getTasks());
        assertEquals(project.getDescription(), res.getBody().getDescription());



    }

    @Test
    void delete() throws JsonProcessingException {

        Project project;
        project = new Project();
        project.setDescription("Projet Test2");
        project.setName("Delete");
        project.setTasks(new ArrayList<Task>());
        project.setUsers(new ArrayList<User>());



       restTemplate.postForEntity("http://localhost:8090/projects", project, String.class);



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

       restTemplate.postForEntity("http://localhost:8090/projects", project, String.class);





        ResponseEntity<Project> res = restTemplate.exchange(
                "http://localhost:8090/projects/name/"+project.getName(), HttpMethod.GET, new HttpEntity<Project>(null, headers), Project.class);

        project.setId(res.getBody().getId());
        project.setName("Updated");




        restTemplate.put("http://localhost:8090/projects", project, String.class);


        ResponseEntity<Project> rs = restTemplate.exchange(
                "http://localhost:8090/projects/"+project.getId(), HttpMethod.GET, new HttpEntity<Project>(null, headers), Project.class);



        assertEquals(project.getName(), rs.getBody().getName());
        assertEquals(project.getId(), rs.getBody().getId());
        assertEquals(project.getUsers(), rs.getBody().getUsers());
        assertEquals(project.getTasks(), rs.getBody().getTasks());
        assertEquals(project.getDescription(), rs.getBody().getDescription());



    }
}