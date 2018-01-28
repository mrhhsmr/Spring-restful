package com.tty.spring.Springrestful;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    //Get
    //uri: -/helloworld
    @GetMapping(path = "/helloworld")
    public String helloworld(){
        return "helloworld";
    }


    @GetMapping(path = "hello/{name}")
    public ResponseEntity<String> getName(@PathVariable String name){
        String hello = "hello " + name;
        HttpHeaders responseheader = new HttpHeaders();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<String>(hello, responseheader, status);
    }

}
