package com.wecode.controller;

import com.wecode.entity.User;
import com.wecode.entity.dto.UserDto;
import com.wecode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
//    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/users", method = RequestMethod.GET)
    public List<User> listUser(){
        return userService.findAll();
    }


    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getOne(@PathVariable(value = "id") Long id){
        return userService.findById(id);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/users", method = RequestMethod.POST)
    public User saveUser(@RequestBody UserDto user){
        return userService.save(user);
    }

    @RequestMapping(value = "auth", method = RequestMethod.GET)
    public String getUserByAuth(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


}
