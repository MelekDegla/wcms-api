package com.wecode.service;

import com.wecode.entity.Task;
import com.wecode.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll(){return taskRepository.findAll();}
    public Task save(Task task){return taskRepository.save(task);}
    public Task update (Task task){ return taskRepository.save(task); }
    public Task findById(Long id){return taskRepository.findById(id).get();}
    public void deleteById(Long id){taskRepository.deleteById(id);}
    public Task findByDescription(String description) {return taskRepository.findByDescription(description).get(0);}
}
