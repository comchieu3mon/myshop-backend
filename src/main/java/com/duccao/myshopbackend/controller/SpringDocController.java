package com.duccao.myshopbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SpringDocController {

  @GetMapping
  public String MyshopDocs() {
    return "index";
  }
}
