package com.wecode.controller;

import com.wecode.entity.Authorization;
import com.wecode.service.AuthorizationService;
import com.wecode.service.EmailService;
import com.wecode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
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
    @Autowired
    EmailService emailService;

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
    @PutMapping(value = "/authorization/accept" )
    public Authorization accept(@RequestBody Authorization authorization){
        authorization.setStatus(1);
        new Thread(() -> {
            try {
                emailService.sendMail(
                        authorization.getUser().getEmail(),
                        "You're authorization is accepted  ",
                         authorization.getDate().toString());

            } catch (MessagingException ex) {
                ex.printStackTrace();
            }
        }).start();
        return authorizationService.update(authorization);
    }
    @PutMapping(value = "/authorization/reject" )
    public Authorization reject(@RequestBody Authorization authorization){
        authorization.setStatus(-1);
        new Thread(() -> {
            try {
                emailService.sendMail(
                        authorization.getUser().getEmail(),
                        "You're authorization is refused  ",
                        authorization.getDate().toString());

            } catch (MessagingException ex) {
                ex.printStackTrace();
            }
        }).start();
        return authorizationService.update(authorization);
    }

}
