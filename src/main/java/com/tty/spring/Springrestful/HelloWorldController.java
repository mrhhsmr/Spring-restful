package com.tty.spring.Springrestful;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    //Get
    //uri: -/helloworld
    @GetMapping(path = "/helloworld")
    public String helloworld(){
        return "helloworld";
    }


}
