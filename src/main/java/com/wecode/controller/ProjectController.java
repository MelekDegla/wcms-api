package com.wecode.controller;

import com.wecode.entity.Project;
import com.wecode.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;


    @GetMapping(value="/projects")
    public List<Project> listProject(){
        return projectService.findAll();
    }


    @GetMapping(value = "/projects/{id}")
    public Project getOne(@PathVariable(value = "id") Long id){
        return projectService.findById(id);
    }

    @PostMapping(value="/projects")
    public Project saveProject(@RequestBody Project project){
        return projectService.save(project);
    }

    @DeleteMapping(value = "/projects/{id}" )
    public void delete(@PathVariable(name = "id") Long id){
        projectService.deleteById(id);
    }
    @PutMapping(value = "/projects" )
    public Project update(@RequestBody Project p){
        return projectService.update(p);
    }
}
