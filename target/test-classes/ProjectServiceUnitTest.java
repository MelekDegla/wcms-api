package com.wecode;

import com.wecode.entity.Project;
import com.wecode.entity.Task;
import com.wecode.repository.ProjectRepository;
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
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class ProjectServiceUnitTest {

    Project project;


    @InjectMocks
    ProjectService projectService;

    @Mock
    ProjectRepository projectRepository;

    @BeforeEach
    void setUp()  {
        MockitoAnnotations.initMocks(this);
        project = new Project();
        project.setDescription("Projet Test");
        project.setName("Test");
        project.setTasks(new ArrayList<Task>());

        assert project.getName().length() >=3 : "Project Name Length Should Be at min 3 chars !";
        assert project.getDescription().length() >=3 : "Project Description Length Should Be at min 3 chars !";
    }

    @Test
    void findAll() {
        List<Project> list = projectService.findAll();
        assertNotNull(list);
    }

    @Test
    void update() {
        String newName= "Update Test";
        String oldName  = project.getName();
        project.setName(newName);

        assert project.getName().length() >=3 : "Project Name Length Should Be at min 3 chars !";
        assert project.getDescription().length() >=3 : "Project Description Length Should Be at min 3 chars !";

        when (projectRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(project));
        Project pr = projectService.findById(project.getId());

        asserts(project, pr);

    }

    @Test
    void save() {
        when (projectRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(project));

        projectRepository.save(project);
        assertNotNull(project.getName());
        assertNotNull(project.getTasks());
        assertNotNull(project.getDescription());




        Project pr = projectService.findById(project.getId());

        asserts(project, pr);


    }

    @Test
    void findById() {
        when (projectRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(project));
        Project pr = projectService.findById(project.getId());
        assertNotNull(pr);

        asserts(project, pr);
    }

    @Test
    void deleteById() {
        long projectId=42;

        projectService.deleteById(projectId);

        verify(projectRepository, times(1)).deleteById(eq(projectId));
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
