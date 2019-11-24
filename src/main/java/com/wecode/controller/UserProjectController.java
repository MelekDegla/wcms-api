package com.wecode.controller;

import com.wecode.entity.UserProject;
import com.wecode.service.UserProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserProjectController {
    @Autowired
    private UserProjectService userProjectService;

    @PostMapping(value="/userprojects")
    public UserProject saveProject(@RequestBody UserProject userProject){

        return userProjectService.save(userProject);
    }

//    @PostMapping(value="userprojects")
//    public UserProject saveProject(@RequestBody UserProjectVM userProject){
//
//         userProjectService.save(userProject);
//         return null;
//    }

}
