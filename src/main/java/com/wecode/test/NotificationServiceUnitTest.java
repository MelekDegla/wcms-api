package com.wecode.test;

import com.wecode.entity.Notification;
import com.wecode.entity.User;
import com.wecode.repository.NotificationRepository;
import com.wecode.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class NotificationServiceUnitTest {
    Notification notification;


    @InjectMocks
    NotificationService notificationService;

    @Mock
    NotificationRepository notificationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        notification = new Notification();
        notification.setBody("Test");
        notification.setTitle("Title");
    }

    @Test
    void findAll() {assertNotNull(notificationService.findAll());}

    @Test
    void save() {
        when (notificationRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(notification));
        notificationRepository.save(notification);
        assertNotNull(notification.getBody());
        assertNotNull(notification.getTitle());



        Notification not = notificationService.findById(notification.getId());


        assertEquals(not.getBody(), notification.getBody());
        assertEquals(not.getBody(), notification.getBody());

    }

    @Test
    void update() {
        String newTitle= "Update Test";
        notification.setTitle(newTitle);

        when (notificationRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(notification));
        Notification not = notificationService.findById(notification.getId());


        assertEquals(not.getBody(), notification.getBody());
        assertEquals(not.getBody(), notification.getBody());
    }

    @Test
    void findById() {
        when (notificationRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(notification));
        Notification not = notificationService.findById(notification.getId());

        assertEquals(not.getBody(), notification.getBody());
        assertEquals(not.getBody(), notification.getBody());
    }

    @Test
    void deleteById() {
        long notificationId=42;

        notificationService.deleteById(notificationId);

        verify(notificationRepository, times(1)).deleteById(eq(notificationId));
    }

    @Test
    void findAllByUser() {
        assertNotNull(notificationRepository.findAllByUser(new User()));
    }
}