package com.wecode.controller;

import com.wecode.entity.Project;
import com.wecode.entity.UserProject;
import com.wecode.entity.dto.ProjectDto;
import com.wecode.service.ProjectService;
import com.wecode.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class ProjectController {

    ModelMapper modelMapper = new ModelMapper();
    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }


    @GetMapping(value="/projects")
    public List<Project> listProject(){

        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN")) ?
                projectService.findAll():  userService.findOne(SecurityContextHolder.getContext().getAuthentication()
                .getName()).getUserProjects().stream().map(UserProject::getProject).collect(Collectors.toList());



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
