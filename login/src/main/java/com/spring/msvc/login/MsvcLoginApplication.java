package com.spring.msvc.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsvcLoginApplication {

  public static void main (String[] args) {
    SpringApplication application = new SpringApplication(MsvcLoginApplication.class);
    application.run(args);
  }
}
