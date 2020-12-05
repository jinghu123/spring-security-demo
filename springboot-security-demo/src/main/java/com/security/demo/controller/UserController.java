package com.security.demo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Secured("ROLE_ADMIN")
    @RequestMapping("/getUser")
    public String getUser(){
         return  "security demo";
    }

}
