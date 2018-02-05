package net.edwardsonthe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

  static final Logger LOGGER = LoggerFactory.getLogger(EurekaServerApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(EurekaServerApplication.class, args);
  }

}