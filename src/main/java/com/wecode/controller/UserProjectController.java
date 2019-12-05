package com.wecode.controller;
import com.sendgrid.*;
import com.wecode.entity.UserProject;
import com.wecode.service.UserProjectService;
import com.wecode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PostMapping(value="userprojects")
    public UserProject saveProject(@RequestBody UserProject userProject) throws IOException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SendGrid sg = new SendGrid("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");
                    Request request = new Request();
                    request.setMethod(Method.POST);
                    request.setEndpoint("mail/send");
//                    Mail mail = new Mail(new Email(userService.findOne(SecurityContextHolder.getContext().getAuthentication()
//                            .getName()).getEmail())), "added to project", new Email(userProject.getUser().getEmail(), new Content("text/plain", userProject.getProject().getName()))
//                    request.setBody();
                    Response response = sg.api(request);
                    System.out.println(response.getStatusCode());
                    System.out.println(response.getBody());
                    System.out.println(response.getHeaders());
                } catch (IOException ex) {
                    try {
                        throw ex;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.run();


        return userProjectService.save(userProject);
    }


}
