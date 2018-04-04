package net.edwardsonthe;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

  @Value("${world.uri}")
  String worldUri;

  @Autowired
  RestTemplateBuilder builder;

  RestTemplate restTemplate;

  @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public Hello hello(@RequestParam(name = "source", defaultValue = "World") String source) throws UnknownHostException {
    LOGGER.info("~~~    uri = {}", worldUri + "/world?source=" + source);
    World world = restTemplate.getForObject(worldUri + "/world?source=" + source, World.class);
    InetAddress inetAddress = InetAddress.getLocalHost();
    Hello hello =
        new Hello(inetAddress.getHostAddress(),
                  inetAddress.getHostName(),
                  System.getProperty("http.proxyHost"),
                  System.getProperty("http.proxyPort"),
                  world);
    LOGGER.info("~~~ {}", hello);
    return hello;
  }

  @PostConstruct
  void postConstruct() {
    LOGGER.info("~~~ worldURI = {}", worldUri);
    restTemplate = builder.build();
  }

}

@AllArgsConstructor
@Getter
@ToString
@XmlRootElement
class Hello {

  String addr;

  String host;

  String proxyHost;

  String proxyPort;

  World world;

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
