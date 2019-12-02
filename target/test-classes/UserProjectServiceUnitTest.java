package com.wecode;

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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserProjectServiceUnitTest {

    UserProject userProject;

    @InjectMocks
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
    void findAll() {
        List<UserProject> list = userProjectService.findAll();
        assertNotNull(list);
    }

    @Test
    void save() {
        when (userProjectRepository.getOne(userProject.getPrimaryKey())).thenReturn(userProject);
        userProjectRepository.save(userProject);

        UserProject up = userProjectRepository.getOne(userProject.getPrimaryKey());
        asserts(up, userProject);


    }


    @Test
    void update() {
        userProject.getUser().setAddress("userProject Adress Test");
        userProject.setManager(false);
        when (userProjectRepository.getOne(userProject.getPrimaryKey())).thenReturn(userProject);
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
