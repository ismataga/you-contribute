package com.example.youcontribute;

import com.example.youcontribute.service.GithubClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class YouContributeApplication {

    public static void main(String[] args) {
        SpringApplication.run(YouContributeApplication.class, args);
    }

}
