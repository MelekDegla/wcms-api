package com.wecode;

import com.wecode.entity.Role;
import com.wecode.repository.RoleRepository;
import com.wecode.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private   RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }




    @Override
    public void run(String... args) throws Exception {
        if(roleRepository.findAll().isEmpty()){
            roleRepository.save(new Role( "ADMIN", "ADMIN"));
            roleRepository.save(new Role( "USER", "USER"));
        }

    }
}
