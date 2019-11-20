package com.wecode.service;

import com.wecode.entity.Log;
import com.wecode.entity.Task;
import com.wecode.repository.LogRepository;
import com.wecode.repository.TaskRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final LogRepository logRepository;

    public TaskService(TaskRepository taskRepository, LogRepository logRepository) {
        this.taskRepository = taskRepository;
        this.logRepository = logRepository;
    }


    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    @Transactional
    public Task save(Task task, String username){
        Task tsk = taskRepository.save(task);

        logRepository.save(new Log("create", username, LocalDateTime.now(), checkStatus(task), checkStatus(task),  tsk));
        return tsk;
    }

    @Transactional
    public Task update (Task task, String username){
        String init = checkStatus(findById(task.getId()));
        Task tsk = taskRepository.save(task);
        logRepository.save(new Log("modify", username, LocalDateTime.now(), init, checkStatus(tsk), tsk));
        return tsk;
    }

    public Task findById(Long id){
        return taskRepository.findById(id).get();
    }

    @Transactional
    public  void deleteById(Long id){
        taskRepository.deleteById(id);
    }

    public String checkStatus (Task tsk)
    {
        String status;
        if (tsk.getStatus().equals(0))
            status = "Problems";
        else if (tsk.getStatus().equals(1))
            status = "To Do";
        else if (tsk.getStatus().equals(2))
            status = "In Progress";
        else if (tsk.getStatus().equals(3))
            status = "To Verify";
        else if (tsk.getStatus().equals(4))
            status = "Done";
        else
            status = "Actions";
        return status;
    }


}
