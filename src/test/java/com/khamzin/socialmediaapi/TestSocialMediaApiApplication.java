package com.khamzin.socialmediaapi;

import com.khamzin.socialmediaapi.util.TestContainerConfig;
import org.springframework.boot.SpringApplication;

public class TestSocialMediaApiApplication {
    public static void main(String[] args) {
        SpringApplication
                .from(SocialMediaApiApplication::main)
                .with(TestContainerConfig.class)
                .run(args);
    }
}
