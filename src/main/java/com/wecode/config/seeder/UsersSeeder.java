package com.wecode.config.seeder;

import com.wecode.entity.User;
import com.wecode.entity.dto.UserDto;
import com.wecode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;

@Component
public class UsersSeeder {

    @Autowired
    private UserService userService;

    public void seed(){
                if(userService.findAll().isEmpty()){
            ArrayList<String> rolesAdmin = new ArrayList<String>();
            rolesAdmin.add("ADMIN");
            ArrayList<String> rolesUser = new ArrayList<String>();
            rolesUser.add("USER");
            ArrayList<String> rolesBoth = new ArrayList<String>();
            rolesBoth.add("ADMIN");
            rolesBoth.add("USER");
            UserDto user1 = new UserDto(null,"Degla" ,"degla123" ,1150L ,
                    LocalDate.of(1997, 6, 7),"address" ,30L ,
                    "13014570", "melek@gmail.com");
            user1.setRoles(rolesBoth);
            userService.save(user1);

                    UserDto user2 = new UserDto(null,"Montassar" ,"monta++" ,1150L ,
                            LocalDate.of(1997, 6, 7),"address" ,30L , "07889877",
                            "tatay@gmail.com");
                    user2.setRoles(rolesAdmin);
                    userService.save(user2);
                    UserDto user3 = new UserDto(null,"wiem" ,"wiemabada" ,1150L ,
                            LocalDate.of(1997, 6, 7),"address" ,30L ,
                            "07898878", "wiem@gmail.com");
                    user3.setRoles(rolesUser);
            userService.save(user3);
        }
    }
}
