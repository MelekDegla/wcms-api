package com.wecode.controller;


import com.wecode.entity.Holiday;
import com.wecode.service.HolidayService;
import com.wecode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class HolidayController {
    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @Autowired
    private UserService userService;
    @GetMapping(value = "/holidays")
    public List<Holiday> findAll() {
        return userService.findOne(SecurityContextHolder.getContext().getAuthentication().getName()).getHolidays();
    }

    @GetMapping(value = "/holidays/{id}")
    public Holiday getOne(@PathVariable(value = "id") Long id){
        return holidayService.findById(id);
    }

    @PostMapping(value="/holidays")
    public Holiday saveHoliday(@RequestBody Holiday holiday){
        holiday.setUser(userService.findOne(SecurityContextHolder.getContext().getAuthentication().getName()));
        return holidayService.save(holiday);
    }

    @DeleteMapping(value = "/holidays/{id}" )
    public void delete(@PathVariable(name = "id") Long id){
        holidayService.deleteById(id);
    }

    @PutMapping(value = "/holidays" )
    public Holiday update(@RequestBody Holiday holiday){
        return holidayService.update(holiday);
    }
}
