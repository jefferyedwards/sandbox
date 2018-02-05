package net.edwardsonthe;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("http://example-producer")
public interface MessageService {

  @RequestMapping(value = "/message", method = RequestMethod.GET)
  String message();
  
}
