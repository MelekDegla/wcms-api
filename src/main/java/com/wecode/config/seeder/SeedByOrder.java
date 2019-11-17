package com.wecode.config.seeder;

import com.wecode.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SeedByOrder {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    RoleSeeder roleSeeder;
    @Autowired
    UsersSeeder usersSeeder;
    @Autowired
    ProjectSeeder projectSeeder;
    @Autowired
    TaskSeeder taskSeeder;

    public void init()  {
        //roles seeder
        roleSeeder.seed();
        usersSeeder.seed();
        projectSeeder.seed();
        taskSeeder.seed();

    }
}
