package com.tty.spring.Springrestful.User;

import com.tty.spring.Springrestful.Exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserJPAResource {

    @Autowired
    private UserRepository userRepository;
    //retrieveAllUsers
    @GetMapping(path = "/jpa/users")
    public List<User> getAll(){
        return userRepository.findAll();
    }

    //retriveUser by id
    @GetMapping(path = "/jpa/users/{id}")
    public Resource<User> getUser(@PathVariable int id){
        User user = userRepository.findOne(id);
        if(user == null){
            throw new UserNotFoundException("id is not valid : " + id);
        }

        //"All-users", SERVER_PATH + "/users
        // RetrieveAllUsers
        Resource<User> resource = new Resource<User>(user);
        //Use Controller LinkBuilder to get the link of getAll() method
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAll());
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    @DeleteMapping(path = "/jpa/users/{id}")
    public void deleteUser(@PathVariable  int id){
        userRepository.delete(id);
    }


    //Save User Service

    /*
    * public void createUser(@RequestBody User user){
    *   User saveduser = service.saveUser(user);
    * }
    * */
    @PostMapping(path = "/jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User saveduser =userRepository.save(user);

        //it will return current uri /users/saveduser.getID()
        URI location = ServletUriComponentsBuilder.
                 fromCurrentRequest().
                 path("/{id}").
                 buildAndExpand(saveduser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
