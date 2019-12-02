package com.wecode.controller;


import com.wecode.entity.Holiday;
import com.wecode.service.HolidayService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class HolidayController {
    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @GetMapping(value = "/holidays")
    public List<Holiday> findAll() {
        return holidayService.findAll();
    }

    @GetMapping(value = "/holidays/{id}")
    public Holiday getOne(@PathVariable(value = "id") Long id){
        return holidayService.findById(id);
    }

    @PostMapping(value="/holidays")
    public Holiday saveProject(@RequestBody Holiday holiday){
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
