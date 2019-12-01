package com.wecode.config.seeder;

import com.wecode.entity.Project;
import com.wecode.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectSeeder {
    @Autowired
    ProjectService projectService;

    public void seed(){
       if(projectService.findAll().isEmpty()) {

            projectService.save(new Project("Project 1", "project wcms"));
            projectService.save(new Project("Project 2", "project wcms"));
            projectService.save(new Project("Project 3", "project wcms"));
            projectService.save(new Project("Project 4", "project wcms"));
        }
        }
}
