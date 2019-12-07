package com.wecode.controller;
import com.sendgrid.*;
import com.wecode.entity.UserProject;
import com.wecode.service.EmailService;
import com.wecode.service.ProjectService;
import com.wecode.service.UserProjectService;
import com.wecode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
@CrossOrigin("*")
public class UserProjectController {
    @Autowired
    private UserProjectService userProjectService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    @Autowired
    EmailService emailService;

    @PostMapping(value="userprojects")
    public UserProject saveProject(@RequestBody UserProject userProject) throws IOException {
        new Thread(() -> {
            try {
                emailService.sendMail(
                        userService.findById(userProject.getUser().getId()).getEmail(),
                        "You'are added to project ",
                        "Nom de project : " + projectService.findById(userProject.getProject().getId()).getName());

            } catch (MessagingException ex) {
                ex.printStackTrace();
            }
        }).start();

        return userProjectService.save(userProject);
    }
}
