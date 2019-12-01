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
        Task tsk = taskService.save(task, SecurityContextHolder.getContext().getAuthentication().getName());

        return tsk;}
    @PutMapping(value = "/tasks")
    public Task update(@RequestBody Task task){
        Task tsk = taskService.update(task, SecurityContextHolder.getContext().getAuthentication().getName());
        simpMessagingTemplate.convertAndSend("/socket-front-project", tsk.getProject());

        return tsk;

    }
    @DeleteMapping(value = "/tasks/{id}")
    public void delete(@PathVariable(name = "id") Long id){taskService.deleteById(id);}

    // For Test
    @GetMapping(value = "/tasks/description/{d}")
    public Task findByDescription(@PathVariable(value = "d") String d){
        return taskService.findByDescription(d);
    }


}
