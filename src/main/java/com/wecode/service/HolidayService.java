package com.wecode.service;

import com.wecode.entity.Holiday;
import com.wecode.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayService {

    private final HolidayRepository holidayRepository;

    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    public Holiday save(Holiday holiday) {return holidayRepository.save(holiday);}

    public Holiday update(Holiday holiday) {return holidayRepository.save(holiday);}

    public void deleteById (long id) {holidayRepository.deleteById(id);}

    public Holiday findById(long id) {return holidayRepository.findById(id).get();}

    public List<Holiday> findAll() {return  holidayRepository.findAll();}
    // For Test
    public Holiday findByEndDate(String endDate) {return holidayRepository.findByEndDate(endDate);}
}