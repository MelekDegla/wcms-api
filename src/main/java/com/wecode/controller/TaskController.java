package com.wecode.controller;

import com.wecode.entity.Task;
import com.wecode.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping(value ="/tasks")
    public List<Task> listTask(){return taskService.findAll();}
    @GetMapping(value = "/tasks/{id}")
    public Task getOne(@PathVariable(value = "id") Long id){ return taskService.findById(id);}
    @PostMapping(value = "/tasks")
    public Task saveTask(@RequestBody Task task){ return taskService.save(task);}
    @PutMapping(value = "/tasks")
    public Task update(@RequestBody Task task){return taskService.update(task);}
    @DeleteMapping(value = "/tasks/{id}")
    public void delete(@PathVariable(name = "id") Long id){taskService.deleteById(id);}


    @GetMapping(value = "/tasks/description/{d}")
    public Task findByDescription(@PathVariable(value = "d") String d){
        return taskService.findByDescription(d);
    }

}
