package com.wecode;

import com.wecode.config.seeder.SeedByOrder;
import com.wecode.entity.User;
import com.wecode.dto.UserDto;
import com.wecode.repository.RoleRepository;
import com.wecode.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private   RoleRepository roleRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private SeedByOrder seedByOrder;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }




    @Override
    public void run(String... args) throws Exception {
//        //roles seeder
//        if(roleRepository.findAll().isEmpty()){
//            roleRepository.save(new Role( "ADMIN", "ADMIN"));
//            roleRepository.save(new Role( "USER", "USER"));
//        }
//        //users Seeder

    }
    @PostConstruct
    public void init(){
        seedByOrder.init();
        User  user = userService.findById(1L);
        userService.save(new UserDto(user.getId(), user.getUsername(), user.getPassword(), 10000000L,user.getBirthdate(), user.getAddress(), user.getLeaveBalance(), user.getCin(), user.getEmail()));
    }
}
