package com.wecode.controller;

import com.wecode.entity.Authorization;
import com.wecode.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorizationController {
    @Autowired
    private AuthorizationService authorizationService ;
    @GetMapping(value="/authorization")
    public List<Authorization> listAuthorization(){
        return authorizationService.findAll();
    }


    @GetMapping(value = "/authorization/{id}")
    public Authorization getOne(@PathVariable(value = "id") Long id){
        return authorizationService.findById(id);
    }

    @PostMapping(value="/authorization")
    public Authorization saveAuthorization(@RequestBody Authorization authorization){
        return authorizationService.save(authorization);
    }

    @DeleteMapping(value = "/authorization/{id}" )
    public void delete(@PathVariable(name = "id") Long id){
        authorizationService.deleteById(id);
    }
    @PutMapping(value = "/authorizations" )
    public Authorization update(@RequestBody Authorization authorization){
        return authorizationService.update(authorization);
    }
}
