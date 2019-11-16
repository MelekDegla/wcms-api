package com.wecode.config.seeder;

import com.wecode.entity.Role;
import com.wecode.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleSeeder {

    @Autowired
    private RoleRepository roleRepository;

    public  void seed(){

        if(roleRepository.findAll().isEmpty()){
            roleRepository.save(new Role( "ADMIN", "ADMIN"));
            roleRepository.save(new Role( "USER", "USER"));
            roleRepository.save(new Role( "USERADMIN", "USER"));
        }
    }
}
