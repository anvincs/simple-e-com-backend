package com.ecom.ecom_proj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api") // base url. every mapping in the class methods will have a /api before it
public class ProductController {

    @Autowired

    @RequestMapping("/") // this is /api
    public String greet() {
        return "Hello World !";
    }
}
