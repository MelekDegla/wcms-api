package test;

import com.wecode.entity.Project;
import com.wecode.entity.Task;
import com.wecode.entity.User;
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

class ProjectServiceTest {

    Project project;


    @InjectMocks
    ProjectService projectService;

    @Mock
    ProjectRepository projectRepository;

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
    void findAll() {
        List<Project> list = projectService.findAll();
        assertNotNull(list);
    }

    @Test
    void update() {
        String newName= "Update Test";
        String oldName  = project.getName();
        project.setName(newName);

        when (projectRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(project));
        Project pr = projectService.findById(project.getId());
        assertEquals(pr.getName(), newName);
    }

    @Test
    void save() {
        when (projectRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(project));

        projectRepository.save(project);
        assertNotNull(project.getName());


        Project pr = projectService.findById(project.getId());

        assertEquals(project.getDescription(), project.getDescription());
    }

    @Test
    void findById() {
        when (projectRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(project));
        Project pr = projectService.findById(project.getId());
        assertNotNull(pr);
        assertEquals(pr.getDescription(), project.getDescription());
    }

    @Test
    void deleteById() {
        long projectId=42;

        projectService.deleteById(projectId);

        verify(projectRepository, times(1)).deleteById(eq(projectId));
    }
}