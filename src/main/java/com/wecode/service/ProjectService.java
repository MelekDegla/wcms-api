package com.wecode.service;

import com.wecode.entity.Project;
import com.wecode.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository   ;

    public List<Project> findAll(){return projectRepository.findAll();}
    public Project update(Project p){
        return projectRepository.save(p);

    }
    public Project save( Project p){ return projectRepository.save(p);    }

    public Project findById(Long id){ return projectRepository.findById(id).get();}

    public void deleteById(Long id){  projectRepository.deleteById(id);}

    public Project findByName(String name) {return projectRepository.findByName(name).get(0);}


}