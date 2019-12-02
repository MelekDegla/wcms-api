package com.wecode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wecode.controller.ProjectController;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectControllerIntegrationTest {

    String url = "http://localhost:8091";
    String authAdmin= "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNb250YXNzYXIiLCJzY29wZXMiOiJST0xFX0FETUlOIiwiaWF0IjoxNTc0OTU4MjEyLCJleHAiOjE1NzUwNDQ2MTJ9.UFOjsGTToDnLRJb-G2p7B2YFfEYJBD4k7pW-kEx4eQE";
    String authNoadmin= "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3aWVtIiwic2NvcGVzIjoiUk9MRV9VU0VSIiwiaWF0IjoxNTc0OTU5MTE0LCJleHAiOjE1NzUwNDU1MTR9.bIWf_UIhW1-I3YRlbYEIoVPUt_igYJPN5pkPoUmzgT0";

    @Mock
    ProjectController projectController;

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
    void listProject() {
        //For Admin Test
        HttpEntity<Project[]> entityFromAdmin = new HttpEntity<Project[]>(null, headersAdmin);
        HttpEntity<List<Project>> response = restTemplate.exchange(
                url+"/projects", HttpMethod.GET, entityFromAdmin, new ParameterizedTypeReference<List<Project>>(){});
        assertNotNull(response.getBody());

        // For No Admin Test
        HttpEntity<Project[]> entityNoAdmin = new HttpEntity<>(null, headersNoAdmin);
        HttpEntity<String> resNoAdmin = restTemplate.exchange(
                url+"/projects", HttpMethod.GET, entityNoAdmin, String.class);
        System.out.println(resNoAdmin.getBody());
        assert resNoAdmin.getBody().contains("Forbidden") ;

        // For No Auth Test
        HttpEntity<Project[]> entity = new HttpEntity<>(null, headersNoAuth);
        HttpEntity<String> resNoAuth = restTemplate.exchange(
                url+"/projects", HttpMethod.GET, entity, String.class);
        assert resNoAuth.getBody().contains("Unauthorized") ;
    }

    @Test
    void getOne() {
        // For Admin Test
        HttpEntity<Project> responseAdmin = restTemplate.exchange(
                url+"/projects/1", HttpMethod.GET, new HttpEntity<Project>(null, headersAdmin), Project.class);

        assertNotNull(responseAdmin.getBody());
        assertEquals("Project 1", responseAdmin.getBody().getName());

        // For No Admin Test
        HttpEntity<Project> responseNoAdmin = restTemplate.exchange(
                url+"/projects/1", HttpMethod.GET, new HttpEntity<Project>(null, headersNoAdmin), Project.class);

        assertNotNull(responseNoAdmin.getBody());
        assertEquals("Project 1", responseAdmin.getBody().getName());

        // For No Auth Test
        HttpEntity<String> responseNoAuth = restTemplate.exchange(
                url+"/projects/1", HttpMethod.GET, new HttpEntity<String>(null, headersNoAuth), String.class);

        assertNotNull(responseNoAuth.getBody());
        assert responseNoAuth.getBody().contains("Unauthorized") ;
    }

    @Test
    void saveProject() {
        Project project;
        project = new Project("ADMINTEST", "ADMINTEST");
        project.setTasks(new ArrayList<Task>());

        // For Admin Test
        HttpEntity<Project> entityAdmin = new HttpEntity<Project>(project, headersAdmin);
        restTemplate.postForEntity(url+"/projects", entityAdmin, String.class);


        HttpEntity<Project> entityFromAdmin = new HttpEntity<Project>(null, headersAdmin);
        ResponseEntity<Project> resAdmin = restTemplate.exchange(
                url+"/projects/name/"+project.getName(), HttpMethod.GET, entityFromAdmin, Project.class);

        assertEquals(project.getName(), resAdmin.getBody().getName());
        assertEquals(project.getTasks(), resAdmin.getBody().getTasks());
        assertEquals(project.getDescription(), resAdmin.getBody().getDescription());

        // For No Admin Test
        Project projectNoAdmin;
        projectNoAdmin = new Project("NOADMINTEST", "NOADMINTEST");
        projectNoAdmin.setTasks(new ArrayList<Task>());
        HttpEntity<Project> entityNoAdmin = new HttpEntity<Project>(projectNoAdmin, headersNoAdmin);
       restTemplate.exchange(url+"/projects", HttpMethod.POST, entityNoAdmin, String.class);


        ResponseEntity<Project> resNoAdmin = restTemplate.exchange(
                url+"/projects/name/"+projectNoAdmin.getName(), HttpMethod.GET, entityNoAdmin, Project.class);
        assertEquals(projectNoAdmin.getName(), resNoAdmin.getBody().getName());
        assertEquals(projectNoAdmin.getTasks(), resNoAdmin.getBody().getTasks());
        assertEquals(projectNoAdmin.getDescription(), resNoAdmin.getBody().getDescription());

        // For No Auth Test

        HttpEntity<Project> entityNoAuth = new HttpEntity<Project>(projectNoAdmin, headersNoAuth);
        try {
            HttpEntity<String> responseNoAAuth = restTemplate.exchange(url + "/projects", HttpMethod.POST, entityNoAuth, String.class);
        }
        catch (Exception e)
        {
            assert e.getMessage().contains("authentication") ;
        }



    }

    @Test
    void delete() {
        // For Admin Test
        Project project;
        project = new Project();
        project.setDescription("Projet Test2");
        project.setName("Delete");
        project.setTasks(new ArrayList<Task>());

        HttpEntity<Project> entityAdmin = new HttpEntity<Project>(project, headersAdmin);
        restTemplate.postForEntity(url+"/projects", entityAdmin, String.class);

        HttpEntity<Project> entityFromAdmin = new HttpEntity<Project>(null, headersAdmin);
        ResponseEntity<Project> res = restTemplate.exchange(
                url+"/projects/name/Delete", HttpMethod.GET, entityFromAdmin, Project.class);

        restTemplate.exchange(url+"/projects/"+res.getBody().getId(), HttpMethod.DELETE, entityFromAdmin, Project.class);

        HttpEntity<Project> res2 = restTemplate.exchange(
                url+"/projects/name/Delete", HttpMethod.GET, entityFromAdmin, Project.class);

        assertNull(res2.getBody().getName());


        // For No Admin Test
        Project projectNoAdmin;
        projectNoAdmin = new Project();
        projectNoAdmin.setDescription("Projet Test2");
        projectNoAdmin.setName("DeleteNoAdmin");
        projectNoAdmin.setTasks(new ArrayList<Task>());

        HttpEntity<Project> entityNoAdmin = new HttpEntity<Project>(projectNoAdmin, headersNoAdmin);
        restTemplate.postForEntity(url+"/projects", entityNoAdmin, String.class);

        HttpEntity<Project> entityNoAdmin1 = new HttpEntity<Project>(null, headersNoAdmin);
        ResponseEntity<Project> resNoAdmin = restTemplate.exchange(
                url+"/projects/name/DeleteNoAdmin", HttpMethod.GET, entityNoAdmin1, Project.class);

        ResponseEntity<Project> resultNoAdmin= restTemplate.exchange(url+"/projects/"+resNoAdmin.getBody().getId(), HttpMethod.DELETE, entityNoAdmin1, Project.class);
        assert resultNoAdmin.getBody() == null;

        // No Auth Test
        HttpEntity<Project> entityNoAuth = new HttpEntity<Project>(null, headersNoAuth);
        ResponseEntity<String> resultNoAuth= restTemplate.exchange(url+"/projects/33", HttpMethod.DELETE, entityNoAuth, String.class);
        assert resultNoAuth.getBody().contains("Unauthorized");


    }

    @Test
    void update() {

        // Admin Test
        Project project;
        project = new Project();
        project.setDescription("Projet Test");
        project.setName("Update");
        project.setTasks(new ArrayList<Task>());


        HttpEntity<Project> entityAdmin = new HttpEntity<Project>(project, headersAdmin);
        restTemplate.postForEntity(url+"/projects", entityAdmin, String.class);

        ResponseEntity<Project> resAdmin = restTemplate.exchange(
                url+"/projects/name/"+project.getName(), HttpMethod.GET, new HttpEntity<Project>(null, headersAdmin), Project.class);

        project.setId(resAdmin.getBody().getId());
        project.setName("Updated");

        restTemplate.put(url+"/projects", entityAdmin, String.class);


        ResponseEntity<Project> rsAdmin = restTemplate.exchange(
                url+"/projects/"+project.getId(), HttpMethod.GET, new HttpEntity<Project>(null, headersAdmin), Project.class);



        assertEquals(project.getName(), rsAdmin.getBody().getName());
        assertEquals(project.getId(), rsAdmin.getBody().getId());
        assertEquals(project.getTasks(), rsAdmin.getBody().getTasks());
        assertEquals(project.getDescription(), rsAdmin.getBody().getDescription());

        // For No Admin Test
        Project projectNoAdmin;
        projectNoAdmin = new Project();
        projectNoAdmin.setDescription("Projetdd Test");
        projectNoAdmin.setName("Updatedd");
        projectNoAdmin.setTasks(new ArrayList<Task>());


        HttpEntity<Project> entityNoAdmin = new HttpEntity<Project>(projectNoAdmin, headersNoAdmin);
        restTemplate.postForEntity(url+"/projects", entityNoAdmin, String.class);

        ResponseEntity<Project> resNoAdmin = restTemplate.exchange(
                url+"/projects/name/"+projectNoAdmin.getName(), HttpMethod.GET, new HttpEntity<Project>(null, headersNoAdmin), Project.class);

        projectNoAdmin.setId(resNoAdmin.getBody().getId());
        projectNoAdmin.setName("Updated");

        restTemplate.put(url+"/projects", entityNoAdmin, String.class);


        ResponseEntity<Project> rsNoAdmin = restTemplate.exchange(
                url+"/projects/"+projectNoAdmin.getId(), HttpMethod.GET, new HttpEntity<Project>(null, headersNoAdmin), Project.class);



        assertEquals(projectNoAdmin.getName(), rsNoAdmin.getBody().getName());
        assertEquals(projectNoAdmin.getId(), rsNoAdmin.getBody().getId());
        assertEquals(projectNoAdmin.getTasks(), rsNoAdmin.getBody().getTasks());
        assertEquals(projectNoAdmin.getDescription(), rsNoAdmin.getBody().getDescription());

        // For No Auth Test
        HttpEntity<Project> entityNoAuth = new HttpEntity<Project>(projectNoAdmin, headersNoAuth);
        try {
            HttpEntity<String> resultNoAuth = restTemplate.exchange(url + "/projects", HttpMethod.PUT, entityNoAuth, String.class);
        }
        catch (Exception e) {
            assert e.getMessage().contains("authentication");
        }
    }
}