package com.wecode.service;

import com.wecode.entity.Authorization;
import com.wecode.repository.AuthorizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationService {

    private final AuthorizationRepository authorizationRepository;

    public AuthorizationService(AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }


    public List<Authorization> findAll(){return authorizationRepository.findAll();}
    public Authorization save(Authorization task){return authorizationRepository.save(task);}
    public Authorization update (Authorization task){ return authorizationRepository.save(task); }
    public Authorization findById(Long id){return authorizationRepository.findById(id).get();}
    public  void deleteById(Long id){authorizationRepository.deleteById(id);}
}
