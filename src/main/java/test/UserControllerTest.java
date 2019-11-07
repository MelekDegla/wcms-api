package test;

import com.wecode.controller.UserController;
import com.wecode.entity.User;
import com.wecode.entity.dto.UserDto;
import com.wecode.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    User user;

    @BeforeEach
    void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setUsername("We Code");
        user.setPassword("123456");
        user.setBirthdate(LocalDate.of(1998, 1, 20));
        user.setSalary(1000);
    }


    @Test
    void listUser() {
        List<User> list = userController.listUser();
        assertNotNull(list);
    }

    @Test
    void getOne() {
        when(userService.findById(anyLong())).thenReturn(user);
        User us = userController.getOne(user.getId());
        assertNotNull(us);
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

    }

    @Test
    void saveUser() {
        when (userService.findById(anyLong())).thenReturn(user);
        UserDto userDto = new UserDto();
        userDto.setBirthdate(user.getBirthdate());
        userDto.setPassword(user.getPassword());
        userDto.setSalary((int) user.getSalary());
        userDto.setUsername(user.getUsername());
        userService.save(userDto);

        assertNotNull(user.getId());


        User us = userController.getOne(user.getId());

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
    }


}