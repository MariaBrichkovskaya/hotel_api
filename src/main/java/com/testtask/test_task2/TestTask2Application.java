package com.testtask.test_task2;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Test Task Part 2",
                version = "1.0.0",
                description = "RESTful API application for working with hotels",
                contact = @Contact(
                        name = "Maria Brichkovskaya",
                        email = "brichkovskayam@gmail.com"
                )

        )
)
public class TestTask2Application {

    public static void main(String[] args) {
        SpringApplication.run(TestTask2Application.class, args);
    }

}
