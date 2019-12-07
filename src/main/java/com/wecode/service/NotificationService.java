package com.wecode.service;

import com.wecode.entity.Notification;
import com.wecode.entity.User;
import com.wecode.repository.NotificationRepository;
import com.wecode.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserServiceImpl userService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    public NotificationService(NotificationRepository notificationRepository, UserServiceImpl userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    public List<Notification> findAll(){
        return notificationRepository.findAll();
    }
    public Notification save(Notification notification){
        Notification notif = notificationRepository.save(notification);
        simpMessagingTemplate.convertAndSend("/notifications/"+ notif.getUser().getUsername(), notif);
        return notif;
    }
    public List<Notification> saveAll(List<Notification> notifications){
        return notificationRepository.saveAll(notifications);
    }

    public Notification update (Notification notification){ return notificationRepository.save(notification); }
    public Notification findById(Long id){return notificationRepository.findById(id).get();}
    public  void deleteById(Long id){notificationRepository.deleteById(id);}
    public List<Notification> findAllByUser( String username){

        return notificationRepository.findAllByUser(userService.findOne(username));
    }

}
