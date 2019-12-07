package com.wecode.controller;

import com.wecode.entity.Authorization;
import com.wecode.service.AuthorizationService;
import com.wecode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class AuthorizationController {
    @Autowired
    private AuthorizationService authorizationService ;

    @Autowired
    private UserService userService;
    @GetMapping(value="/authorization")
    public List<Authorization> listAuthorization(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN")) ? authorizationService.findAll() :
         userService.findOne(SecurityContextHolder.getContext().getAuthentication().getName()).getAuthorizations();
    }


    @GetMapping(value = "/authorization/{id}")
    public Authorization getOne(@PathVariable(value = "id") Long id){
        return authorizationService.findById(id);
    }

    @PostMapping(value="/authorization")
    public Authorization saveAuthorization(@RequestBody Authorization authorization){
        authorization.setUser(userService.findOne(SecurityContextHolder.getContext().getAuthentication().getName()));

        return authorizationService.save(authorization);
    }

    @DeleteMapping(value = "/authorization/{id}" )
    public void delete(@PathVariable(name = "id") Long id){
        authorizationService.deleteById(id);
    }
    @PutMapping(value = "/authorization" )
    public Authorization update(@RequestBody Authorization authorization){
        return authorizationService.update(authorization);
    }
}