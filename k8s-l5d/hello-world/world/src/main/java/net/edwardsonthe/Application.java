package net.edwardsonthe;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}

@RestController
class Controller {

  final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

  @RequestMapping(value = "/world", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public World world(@RequestParam(name = "source", required = true) String source) throws UnknownHostException {
    LOGGER.info("~~~ source = {}", source);
    InetAddress inetAddress = InetAddress.getLocalHost();
    World world =
        new World(inetAddress.getHostAddress(),
                  inetAddress.getHostName(),
                  System.getProperty("http.proxyHost"),
                  System.getProperty("http.proxyPort"),
                  source);
    LOGGER.info("~~~ {}", world);
    return world;
  }

}

@AllArgsConstructor
@Getter
@ToString
class World {

  String addr;

  String host;

  String proxyHost;

  String proxyPort;

  String source;

}
