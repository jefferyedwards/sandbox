package net.edwardsonthe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}

@RestController
class Controller {

  final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

  @RequestMapping(value = "/world", method = RequestMethod.GET, produces = "test/plain")
  public String world(@RequestParam(name = "source", required = true) String source) {
    LOGGER.info("~~~ source = {}", source);
    return "... " + source;
  }

}
