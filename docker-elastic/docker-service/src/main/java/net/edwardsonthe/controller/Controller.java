package net.edwardsonthe.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import net.edwardsonthe.domain.Event;

@RestController
@Slf4j
public class Controller {

  static final ObjectMapper MAPPER = new ObjectMapper();


  @RequestMapping(path = "/event", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HttpStatus> event(@RequestBody Event event) throws IOException {
    log.info("eventTimestamp={}, eventName={}, eventData={}", event.getTimestamp(), event.getName(), event.getData());
    return ResponseEntity.ok(HttpStatus.OK);
  }

}