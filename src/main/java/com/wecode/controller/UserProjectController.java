package com.wecode.controller;
import com.sendgrid.*;
import com.wecode.entity.UserProject;
import com.wecode.service.UserProjectService;
import com.wecode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class UserProjectController {
    @Autowired
    private UserProjectService userProjectService;
    @Autowired
    private UserService userService;

    @PostMapping(value="userprojects")
    public UserProject saveProject(@RequestBody UserProject userProject) throws IOException {
        try {
            SendGrid sg = new SendGrid("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");
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody("{\"personalizations\":[{\"to\":[{\"email\":\"" +
                    userProject.getUser().getEmail()+
                    "\"}]," +
                    "\"subject\":\"You're added to a new Project\"}]," +
                    "\"from\":{\"email\":\"" +
                            userService.findOne(SecurityContextHolder.getContext().getAuthentication()
                                    .getName()).getEmail()+
                    "\"}," +
                    "\"content\":[{\"type\":\"text/plain\"," +
                    "\"value\": \"" +
                    userProject.getProject().getName() +
                    "\"}]}");
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
        return userProjectService.save(userProject);
    }


}
