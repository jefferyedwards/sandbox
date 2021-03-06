package net.edwardsonthe.demo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableScheduling
public class Application {

  public static void main(String[] args) { SpringApplication.run(Application.class, args); }

}

@RestController
class Controller {

  @Autowired
  ScheduledTasks scheduledTasks;

  @RequestMapping(value = "/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public String status() { return scheduledTasks.getStatus(); }

}

@Component
class ScheduledTasks {

  static final String hostAddress;
  static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
  static final String message = "hostAddress=%s estimate=%.7f delta=%.7f";
  static final Random rand = new Random();

  static {
    String value;
    try {
      value = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      value = "unkwon";
    }
    log.info("hostAddress={}", value);
    hostAddress = value;
  }

  String status = "not-set";

  @Scheduled(fixedRate = 200)
  void calc() {

    int cnt = 0, max = 10_000_000;

    for (int i = 0; i < max; i++) {
      double a = 2 * rand.nextDouble() - 1, b = 2 * rand.nextDouble() - 1;
      double c = Math.sqrt(a * a + b * b);
      if (c <= 1) cnt++;
    }

    double estimate = (4.0d * cnt) / max, delta = estimate - Math.PI;

    String status = String.format(message, hostAddress, estimate, delta);
    log.info(status);
    setStatus(status);

  }

  synchronized String getStatus() { return this.status; }

  synchronized void setStatus(String status) { this.status = status; }

}
