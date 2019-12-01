package com.wecode.test;


import com.wecode.controller.ProjectController;
import com.wecode.entity.Project;
import com.wecode.entity.Task;
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

class ProjectControllerUnitTest {


    Project project;

    @InjectMocks
    ProjectController projectController;

    @Mock
    ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        project = new Project();
        project.setDescription("Projet Test");
        project.setName("Test");
        project.setTasks(new ArrayList<Task>());

        assert project.getName().length() >=3 : "Project Name Length Should Be At Least 3 !";
        assert project.getDescription().length() >=3 : "Project Description Length Should Be At Least 3 !";
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

        asserts(project, pr);
    }

    @Test
    void saveProject() {
        when (projectService.findById(anyLong())).thenReturn(project);

        projectService.save(project);
        assertNotNull(project.getName());
        assertNotNull(project.getTasks());
        assertNotNull(project.getDescription());


        Project pr = projectController.getOne(project.getId());
        assertNotNull(pr);

        asserts(project, pr);
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
        assertNotNull(pr);

        asserts(project, pr);

    }

    private void asserts(Project project, Project pr)
    {
        assertEquals(pr.getDescription(), project.getDescription());
        assertEquals(pr.getName(), project.getName());
        assertEquals(pr.getTasks(), project.getTasks());

        assert pr.getName().length() >=3 : "Project Name Length Should Be at min 3 chars !";
        assert pr.getDescription().length() >=3 : "Project Description Length Should Be at min 3 chars !";
        assert project.getName().length() >=3 : "Project Name Length Should Be at min 3 chars !";
        assert project.getDescription().length() >=3 : "Project Description Length Should Be at min 3 chars !";
    }
}
