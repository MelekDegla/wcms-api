package com.wecode.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.wecode.entity.Notification;
import com.wecode.entity.User;

import com.wecode.entity.dto.UserDto;
import com.wecode.entity.util.ChangePasswordVM;
import com.wecode.service.NotificationService;
import com.wecode.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
   @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/users", method = RequestMethod.GET)
    public List<User> listUser(){
        //return userService.findAll().stream().map(user -> modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
        return userService.findAll();
   }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getOne(@PathVariable(value = "id") Long id){
        //return modelMapper.map(userService.findById(id),UserDto.class);
        return userService.findById(id);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/users", method = RequestMethod.POST)
    public User saveUser(@RequestBody UserDto user){
        return userService.save(user);
//        return modelMapper.map(userService.save(user),UserDto.class);
    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public User getUserByAuth() {
        return userService.findOne(SecurityContextHolder.getContext().getAuthentication().getName());
//        return modelMapper.map(userService.findOne(SecurityContextHolder.getContext().getAuthentication().getName()),UserDto.class);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
         userService.delete(id);
    }

    @RequestMapping(value="/users", method = RequestMethod.PUT)
    public User modifyUser(@RequestBody UserDto user){
       return userService.save(user);
//       return modelMapper.map(userService.save(user),UserDto.class);
    }

    @RequestMapping(value = "/notifs", method = RequestMethod.GET)
    public List<Notification> getUserNotifications(){
       return notificationService.findAllByUser(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @RequestMapping(value = "/notifs/viewed")
    public List<Notification> markAsViewed(){
       User user = userService.findOne(SecurityContextHolder.getContext().getAuthentication().getName());
       List<Notification> notifications = user.getNotifications();
       List<Notification> notificationsnew = new ArrayList<>();

       notifications.forEach(n ->{
           n.setViewed(true);
           notificationsnew.add(n) ;
       } );
       return notificationService.saveAll(notificationsnew);
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public Boolean changePassword(@RequestBody ChangePasswordVM user)
    {
        User us = userService.findOne(SecurityContextHolder.getContext().getAuthentication().getName());
        return userService.changePassword(user, us.getUsername());
    }

}
