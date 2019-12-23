/*
package com.wecode;


import com.wecode.controller.ProjectController;
import com.wecode.entity.Project;
import com.wecode.entity.Task;
import com.wecode.entity.dto.ProjectDto;
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

    ProjectDto projectDTO;
    Project project;

    @InjectMocks
    ProjectController projectController;

    @Mock
    ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        project = new Project();
        project.setId(1L);
        project.setDescription("Prosjet Test");
        project.setName("Tesst");
        project.setTasks(new ArrayList<Task>());

        projectDTO = new ProjectDto();
        projectDTO.setDescription("Prsojet Test");
        projectDTO.setName("Tesst");
        projectDTO.setTasks(new ArrayList<Task>());

        assert project.getName().length() >=3 : "Project Name Length Should Be At Least 3 !";
        assert project.getDescription().length() >=3 : "Project Description Length Should Be At Least 3 !";
    }

    @Test
    void listProject() {
        assertNotNull(projectController.listProject());
    }

    @Test
    void getOne() {
        when(projectService.findById(anyLong())).thenReturn(project);
        //when(projectController.getOne(anyLong())).thenReturn(projectDTO);

        ProjectDto pr = projectController.getOne(project.getId());
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


        ProjectDto pr = projectController.getOne(project.getId());
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

        when (projectController.getOne(anyLong())).thenReturn(projectDTO);
        Project pr = projectService.findById(project.getId());
        assertNotNull(pr);

        asserts(project, pr);

    }

    private void asserts(Project project, ProjectDto pr)
    {
        assertEquals(pr.getDescription(), project.getDescription());
        assertEquals(pr.getName(), project.getName());
        assertEquals(pr.getTasks(), project.getTasks());

        assert pr.getName().length() >=3 : "Project Name Length Should Be at min 3 chars !";
        assert pr.getDescription().length() >=3 : "Project Description Length Should Be at min 3 chars !";
        assert project.getName().length() >=3 : "Project Name Length Should Be at min 3 chars !";
        assert project.getDescription().length() >=3 : "Project Description Length Should Be at min 3 chars !";
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
*/
