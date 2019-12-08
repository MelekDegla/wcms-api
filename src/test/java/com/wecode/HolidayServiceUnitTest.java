package com.wecode;

import com.wecode.entity.Holiday;
import com.wecode.entity.User;
import com.wecode.repository.HolidayRepository;
import com.wecode.service.HolidayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class HolidayServiceUnitTest {

    Holiday holiday;


    @InjectMocks
    HolidayService holidayService;

    @Mock
    HolidayRepository holidayRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        holiday = new Holiday();
        holiday.setId(2L);
        holiday.setStartDate("03/12/2019");
        holiday.setEndDate("04/12/2019");
        holiday.setStatus(2);
        holiday.setUser(new User());
    }

    @Test
    void save() {
        when (holidayRepository.findById(anyLong())).thenReturn(Optional.ofNullable(holiday));
        holidayRepository.save(holiday);
        asserts(holiday);



        Holiday hd = holidayService.findById(holiday.getId());
        asserts(holiday, hd);
    }



    @Test
    void update() {
        int newIsValidated = 0;
        holiday.setStatus(newIsValidated);

        when (holidayRepository.findById(anyLong())).thenReturn(Optional.ofNullable(holiday));
            Holiday hd = holidayService.findById(holiday.getId());
        assertEquals(hd.getStatus(), newIsValidated);

    }

    @Test
    void deleteById() {
        long holidayId=42;

        holidayService.deleteById(holidayId);

        verify(holidayRepository, times(1)).deleteById(eq(holidayId));
    }

    @Test
    void findById() {
        when (holidayRepository.findById(anyLong())).thenReturn(Optional.ofNullable(holiday));
        Holiday hd = holidayService.findById(holiday.getId());
        asserts(holiday, hd);
    }

    @Test
    void findAll() {
        assertNotNull(holidayService.findAll());
    }


    private void asserts(Holiday holiday) {
        assertNotNull(holiday.getEndDate());
        assertNotNull(holiday.getStartDate());
        assertNotNull(holiday.getUser());
    }
    private void asserts(Holiday holiday, Holiday hd)
    {
        asserts(holiday);
        asserts(hd);
        assertEquals(holiday.getEndDate(), hd.getEndDate());
        assertEquals(holiday.getStartDate(), hd.getStartDate());
        assertEquals(holiday.getUser(), hd.getUser());
    }
}
