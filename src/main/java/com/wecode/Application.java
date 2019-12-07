package com.wecode;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import com.wecode.config.seeder.SeedByOrder;
import com.wecode.entity.User;
import com.wecode.entity.dto.UserDto;
import com.wecode.proprety.FileStorageProperties;
import com.wecode.repository.RoleRepository;
import com.wecode.service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
public class Application implements CommandLineRunner {
    @Autowired
    private   RoleRepository roleRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private SeedByOrder seedByOrder;

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }
    public static void main(String[] args) {
       SpringApplication.run(Application.class, args);

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
    public void init() throws MailjetSocketTimeoutException, MailjetException {
        seedByOrder.init();
        User  user = userService.findById(1L);
        userService.save(new UserDto(user.getId(), user.getUsername(), user.getPassword(), 10000000L,user.getBirthdate(), user.getAddress(), user.getLeaveBalance(), user.getCin(), user.getEmail()));
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        client = new MailjetClient("93c373f34156c25b304ffa292f30d7b6", "ff6a12a1ffb0dee28c9b1ce37dcbe543", new ClientOptions("v3.1"));
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "melek.degla14@gmail.com")
                                        .put("Name", "Melek"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", "melek199767@gmail.com")
                                                .put("Name", "Melek")))
                                .put(Emailv31.Message.SUBJECT, "Greetings from Mailjet.")
                                .put(Emailv31.Message.TEXTPART, "My first Mailjet email")
                                .put(Emailv31.Message.HTMLPART, "<h3>Dear passenger 1, welcome to <a href='https://www.mailjet.com/'>Mailjet</a>!</h3><br />May the delivery force be with you!")
                                .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));
        response = client.post(request);
        System.out.println(response.getStatus());
        System.out.println(response.getData());

    }
}
