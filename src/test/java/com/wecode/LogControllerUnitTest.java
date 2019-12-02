package com.wecode;

import com.wecode.controller.LogController;
import com.wecode.service.LogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LogControllerUnitTest {


    @InjectMocks
    LogController logController;

    @Mock
    LogService logService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAll() {
        assertNotNull(logController.findAll());
    }
}