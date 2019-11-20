package com.wecode.controller;

import com.wecode.entity.Task;
import com.wecode.repository.UserProjectRepository;
import com.wecode.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserProjectRepository userProjectRepository;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping(value ="/tasks")
    public List<Task> listTask(){return taskService.findAll();}
    @GetMapping(value = "/tasks/{id}")
    public Task getOne(@PathVariable(value = "id") Long id){ return taskService.findById(id);}
    @PostMapping(value = "/tasks")
    public Task saveTask(@RequestBody Task task){
        task.setProject(taskService.findById(task.getId()).getProject());
        Task tsk = taskService.save(task, SecurityContextHolder.getContext().getAuthentication().getName());
        this.simpMessagingTemplate.convertAndSend("/socket-front-project",tsk.getProject());

       userProjectRepository.findAllByProjectId(task.getProject().getId())
               .forEach(up -> {
                   System.out.println("------------------------------------------------------------------------------");
                   System.out.println(up.getUser().getUsername());
                   System.out.println("------------------------------------------------------------------------------");
                    this.simpMessagingTemplate.convertAndSend("/notifications/"+up.getUser().getUsername(), tsk);
               });
        return tsk;}
    @PutMapping(value = "/tasks")
    public Task update(@RequestBody Task task){
        Task tsk = taskService.update(task, SecurityContextHolder.getContext().getAuthentication().getName());

        this.simpMessagingTemplate.convertAndSend("/socket-front-project","test-------------------\n --------------------");

        return tsk;

    }
    @DeleteMapping(value = "/tasks/{id}")
    public void delete(@PathVariable(name = "id") Long id){taskService.deleteById(id);}

}
