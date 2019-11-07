package test;

import com.wecode.entity.User;
import com.wecode.repository.UserRepository;
import com.wecode.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.time.LocalDate;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


class UserServiceImplTest {

    User user;


    @InjectMocks
    UserServiceImpl userServiceImp;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setUsername("We Code");
        user.setPassword("123456");
        user.setBirthdate(LocalDate.of(1998, 1, 20));
        user.setSalary(1000);
        user.setCin("15009308");
        user.setEmail("karim@gmail.com");
        user.setLeaveBalance((long)0);
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
    void findById() {
        when (userRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(user));
        User us = userServiceImp.findById(user.getId());
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
    void save() {
        when (userRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(user));

        userRepository.save(user);
        assertNotNull(user.getId());


        User us = userServiceImp.findById(user.getId());

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