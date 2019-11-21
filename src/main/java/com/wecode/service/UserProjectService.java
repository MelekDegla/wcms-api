package com.wecode.service;



import com.wecode.entity.UserProject;
import com.wecode.entity.util.UserProjectVM;
import com.wecode.repository.UserProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProjectService {
    private final UserProjectRepository userProjectRepository;

    public UserProjectService(UserProjectRepository userProjectRepository) {
        this.userProjectRepository = userProjectRepository;
    }

    public List<UserProject> findAll() {
        return userProjectRepository.findAll();
    }
//    public void save(UserProjectVM userProject) {
//         userProjectRepository.saveUP(userProject.getIdUser(), userProject.getIdProject(), userProject.isManager());
//
//    }
public UserProject save(UserProject userProject) {
        return userProjectRepository.save(userProject);
}
    public UserProject update (UserProject userProject) { return userProjectRepository.save(userProject); }
   // public UserProject findById(Long id){return userProjectRepository.findById(id).get();}
   // public  void deleteById(Long id){userProjectRepository.deleteById(id);}
}
