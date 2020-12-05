package com.security.demo.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.rmi.AccessException;
import java.security.AccessControlException;

@ControllerAdvice
public class HandlerControllerException {

    @ExceptionHandler(RuntimeException.class)
    public String handlerException(RuntimeException e){

        if(e instanceof AccessControlException){
           return "redirect:/403.html";
        }
        return "redirect:/403.html";
    }
}
