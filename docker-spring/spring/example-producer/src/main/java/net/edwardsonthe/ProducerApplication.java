package net.edwardsonthe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
public class ProducerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProducerApplication.class, args);
  }

}

@RefreshScope
@RestController
@EnableConfigurationProperties(ProducerProperties.class)
class MessageServiceController {

  @Autowired
  ProducerProperties producerProperties;
  
  @RequestMapping(value = "/message", method = RequestMethod.GET)
  public String message() {
    return producerProperties.getMessage() +"\n";
  }

}