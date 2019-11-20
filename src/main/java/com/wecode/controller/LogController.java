package com.wecode.controller;

import com.wecode.entity.Log;
import com.wecode.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping(value = "/logs")
    public List<Log> findAll() {
        return logService.findAll();
    }
}
