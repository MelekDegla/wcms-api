package test;


import com.wecode.controller.ProjectController;
import com.wecode.entity.Project;
import com.wecode.entity.Task;
import com.wecode.entity.User;
import com.wecode.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class ProjectControllerTest {
    Project project;

    @InjectMocks
    ProjectController projectController;

    @Mock
    ProjectService projectService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        project = new Project();
        project.setDescription("Projet Test");
        project.setName("Test");
        project.setTasks(new ArrayList<Task>());
        project.setUsers(new ArrayList<User>());
    }

    @Test
    void listProject() {
        List<Project> list = projectController.listProject();
        assertNotNull(list);
    }

    @Test
    void getOne() {
        when(projectService.findById(anyLong())).thenReturn(project);
        Project pr = projectController.getOne(project.getId());
        assertNotNull(pr);
        assertEquals(pr.getDescription(), project.getDescription());
        assertEquals(pr.getName(), project.getName());
        assertEquals(pr.getTasks(), project.getTasks());
        assertEquals(pr.getUsers(), project.getUsers());
    }

    @Test
    void saveProject() {
        when (projectService.findById(anyLong())).thenReturn(project);

        projectService.save(project);
        assertNotNull(project.getName());
        assertNotNull(project.getUsers());
        assertNotNull(project.getTasks());
        assertNotNull(project.getDescription());


        Project pr = projectController.getOne(project.getId());

        assertEquals(pr.getDescription(), project.getDescription());
        assertEquals(pr.getName(), project.getName());
        assertEquals(pr.getTasks(), project.getTasks());
        assertEquals(pr.getUsers(), project.getUsers());
    }

    @Test
    void delete() {
        long projectId=42;

        projectController.delete(projectId);

        verify(projectService, times(1)).deleteById(eq(projectId));
    }

    @Test
    void update() {
        String newName= "Update Test";
        String oldName  = project.getName();
        project.setName(newName);

        when (projectController.getOne(anyLong())).thenReturn(project);
        Project pr = projectService.findById(project.getId());
        assertEquals(pr.getName(), newName);

        assertNotNull(pr.getDescription());
        assertNotNull(pr.getTasks());
        assertNotNull(pr.getUsers());

    }
}