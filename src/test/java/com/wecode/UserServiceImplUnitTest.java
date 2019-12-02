package com.wecode;

import com.wecode.entity.User;
import com.wecode.repository.UserRepository;
import com.wecode.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


class UserServiceImplUnitTest {

    User user;


    @InjectMocks
    UserServiceImpl userServiceImp;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setUsername("We Code");
        user.setPassword("123456");
        user.setBirthdate("1998-01-20");
        user.setSalary(1000);
        user.setCin("15009308");
        user.setEmail("karim@gmail.com");
        user.setLeaveBalance((long)0);

        assertNotNull(user.getUsername());
        assertNotNull(user.getPassword());
        assertNotNull(user.getBirthdate());
        assertNotNull(user.getCin());
        assertNotNull(user.getEmail());

        assert user.getUsername().length() >=3 : "Username Length Should Be At Least 3!";
        assert user.getPassword().length() >=6 : "Password Length Should Be At Least 3!";
    }



    @Test
    void findAll() {
        List<User> list = userServiceImp.findAll();
        assertNotNull(list);

    }

    @Test
    void delete() {
        long userId=42;

        userServiceImp.delete(userId);

        verify(userRepository, times(1)).deleteById(eq(userId));
    }

    @Test
    void findOne() {
        when (userServiceImp.findOne(anyString())).thenReturn(user);
        User us = userServiceImp.findOne("We Code");
        asserts(us, user);
    }

    @Test
    void findById() {
        when (userRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(user));
        User us = userServiceImp.findById(user.getId());
        assertNotNull(us);
        asserts(us, user);
    }

    @Test
    void save() {
        when (userRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(user));

        userRepository.save(user);
        assertNotNull(user.getId());


        User us = userServiceImp.findById(user.getId());

        asserts(us, user);

    }

    private void asserts(User us, User user) {
        assertNotNull(us);
        assertEquals(us.getPassword(), user.getPassword());
        assertEquals(us.getBirthdate(), user.getBirthdate());
        assertEquals(us.getSalary(), user.getSalary());
        assertEquals(us.getUsername(), user.getUsername());
        assertEquals(us.getRoles(), user.getRoles());
        assertEquals(us.getAddress(), user.getAddress());
        assertEquals(us.getCin(), user.getCin());
        assertEquals(us.getEmail(), user.getEmail());
        assertEquals(us.getLeaveBalance(), user.getLeaveBalance());

        assert user.getUsername().length() >=3 : "Username Length Should Be At Least 3!";
        assert us.getPassword().length() >=6 : "Password Length Should Be At Least 3!";

    }
}
