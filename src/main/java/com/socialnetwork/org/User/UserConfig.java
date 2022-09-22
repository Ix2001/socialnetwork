package com.socialnetwork.org.User;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;

import static java.time.Month.APRIL;

@Configuration
public class UserConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository){
        return args -> {
           UserData admin = new UserData(
                    "Admin",
                    "Admin",
                    "Admin@gmail.com",
                    "Admin",
                    LocalDate.of(2001, APRIL,4)
            );
        };
    }
}
