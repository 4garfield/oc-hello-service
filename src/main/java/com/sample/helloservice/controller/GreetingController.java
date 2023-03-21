package com.sample.helloservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sample.helloservice.model.Greeting;

@RestController
@RequestMapping("/app")
public class GreetingController {

  private static final String template = "Hello, %s!";

  @GetMapping("/greeting")
  @ResponseBody
  public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new Greeting(String.format(template, name));
  }
}
