package com.wecode;

import com.wecode.controller.UserProjectController;
import com.wecode.entity.Project;
import com.wecode.entity.Task;
import com.wecode.entity.User;
import com.wecode.entity.UserProject;
import com.wecode.repository.UserProjectRepository;
import com.wecode.service.UserProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class UserProjectControllerUnitTest {

    UserProject userProject;

    @InjectMocks
    UserProjectController userProjectController;

    @Mock
    UserProjectService userProjectService;

    @Mock
    UserProjectRepository userProjectRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userProject = new UserProject();
        userProject.setManager(true);
        User user = new User();
        user.setUsername("We Code");
        user.setPassword("123456");
        user.setBirthdate("1998-01-20");
        user.setSalary(1000);
        user.setCin("15009308");
        user.setEmail("karim@gmail.com");
        user.setLeaveBalance((long)0);
        userProject.setUser(user);

        Project project = new Project();
        project.setDescription("Projet Test");
        project.setName("Test");
        project.setTasks(new ArrayList<Task>());

        userProject.setProject(project);

    }

    @Test
    void saveProject() {


        when (userProjectRepository.getOne(userProject.getPrimaryKey())).thenReturn(userProject);
        userProjectService.save(userProject);
        UserProject up = userProjectRepository.getOne(userProject.getPrimaryKey());
        asserts(up, userProject);
    }

    private void asserts(UserProject userProject) {
        assertNotNull(userProject);
        assertNotNull(userProject.getPrimaryKey());
        assertNotNull(userProject.getProject());
        assertNotNull(userProject.getUser());
    }



    private void asserts(UserProject up, UserProject userProject) {
        asserts(userProject);
        asserts(up);
        assertEquals(up.getPrimaryKey(), userProject.getPrimaryKey());
        assertEquals(up.getProject(), userProject.getProject());
        assertEquals(up.getUser(), userProject.getUser());
        assertEquals(up.isManager(), userProject.isManager());
    }
}
