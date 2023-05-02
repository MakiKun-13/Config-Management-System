package com.example.configmanagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.example.*"})
public class ConfigManagementApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(ConfigManagementApplication.class, args);
    }

    @Override
    public void run(String... args) {
    }
}
