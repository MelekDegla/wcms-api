package com.wecode.config.seeder;

import com.wecode.entity.Task;
import com.wecode.repository.ProjectRepository;
import com.wecode.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TaskSeeder {
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProjectRepository projectRepository;

    public void seed(){
        if(taskService.findAll().isEmpty())
        projectRepository.findAll().forEach(p -> {
            ArrayList<String> test = new ArrayList<>();
            test.add("dgjbhjkl");
            test.add("jn");
            test.add("kjnikn");
            test.add("k,");
            taskService.save(new Task("Task 1 ", "Task1", 1, p,test));
            taskService.save(new Task("Task 2 ", "Task2", 1, p));
            taskService.save(new Task("Task 3 ", "Task3", 1, p));
            taskService.save(new Task("Task 4 ", "Task4", 1, p));
            taskService.save(new Task("Task 5 ", "Task5", 1, p));
            taskService.save(new Task("Task 6 ", "Task6", 1, p));

        });
    }
}
