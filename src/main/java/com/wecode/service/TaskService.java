package com.wecode.service;

import com.wecode.entity.Log;
import com.wecode.entity.Notification;
import com.wecode.entity.Project;
import com.wecode.entity.Task;
import com.wecode.repository.LogRepository;
import com.wecode.repository.ProjectRepository;
import com.wecode.repository.TaskRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final LogRepository logRepository;
    private final NotificationService notificationService;
    private final ProjectRepository projectRepository;


    public TaskService(TaskRepository taskRepository, LogRepository logRepository, NotificationService notificationService, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.logRepository = logRepository;
        this.notificationService = notificationService;
        this.projectRepository = projectRepository;
    }


    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    @Transactional
    public Task save(Task task, String username){
        Task tsk = taskRepository.save(task);

        Project project = projectRepository.findById(tsk.getProject().getId()).get();
           project.getUserProjects().forEach(
                    up -> {
                        if(!username.equals(up.getUser().getUsername()))
                          notificationService.save(new Notification(username, "has created task "+tsk.getLabel()+" in project named "+tsk.getProject().getName(), "/scrumboard/"+ tsk.getProject().getId(), up.getUser()));

                    } );



        logRepository.save(new Log("created task named" + tsk.getLabel() , username, LocalDateTime.now(), checkStatus(task), checkStatus(task),  tsk));
        return tsk;
    }

    @Transactional
    public Task update (Task task, String username){
        String init = checkStatus(findById(task.getId()));
        Task task1 = findById(task.getId());
        task.setResources(task1.getResources());
        task.setUsernames(task1.getUsernames());
        Task tsk = taskRepository.save(task);

        Project project = projectRepository.findById(tsk.getProject().getId()).get();
        project.getUserProjects().forEach(
                up -> {
                    if(!username.equals(up.getUser().getUsername()))
                    notificationService.save(new Notification(username, "has modified task "+tsk.getLabel()+" in project named "+tsk.getProject().getName(), "/scrumboard/"+ tsk.getProject().getId(), up.getUser()));

                } );
        logRepository.save(new Log("modified task named "+ task.getLabel(), username, LocalDateTime.now(), init, checkStatus(tsk), tsk));
        return tsk;
    }

    public Task update (Task task) {
        Task tsk = findById(task.getId());
        task.setResources(tsk.getResources());
       return taskRepository.save(task);
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

    // For Test
    public Task findByDescription(String description) {return taskRepository.findByDescription(description).get(0);}


}
