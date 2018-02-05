package net.edwardsonthe;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

  static final Logger LOGGER = LoggerFactory.getLogger(ConfigServerApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(ConfigServerApplication.class, args);
  }

  @Value("${privateKey}")
  String privateKey;

  @Value("${uri}")
  String uri;

  @PostConstruct
  void postConstruct() {
    LOGGER.info("vault configuration > privateKey=\n{}\n uri={}", privateKey, uri);
  }

}
