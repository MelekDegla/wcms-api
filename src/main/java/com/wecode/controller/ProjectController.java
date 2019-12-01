package com.wecode.controller;

import com.wecode.entity.Project;
import com.wecode.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*" +
        "")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PreAuthorize("hasRole('ADMIN')")
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


    // For TEST
    @GetMapping(value = "/projects/name/{n}")
    public Project findByName(@PathVariable(value = "n") String name){
        return projectService.findByName(name);
    }
}
