package com.tty.spring.Springrestful.User;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
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

@RestController
public class UserResource {

    @Autowired
    private UserDaoService service;
    //retrieveAllUsers
    @GetMapping(path = "/users")
    public List<User> getAll(){
        return service.getAllUsers();
    }

    //retriveUser by id
    @GetMapping(path = "/users/{id}")
    public Resource<User> getUser(@PathVariable int id){
        User user = service.getUser(id);
        if(user == null){
            throw new UserNotFoundException("id is not valid : " + id);
        }

        //resource stands for the
        Resource<User> resource = new Resource<User>(user);
        //Use Controller LinkBuilder to get the link of getAll() method
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAll());
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteUser(@PathVariable  int id){
        User user = service.DeleteById(id);
        if(user == null){
            throw new UserNotFoundException("id is not valid : " + id);
        }
    }


    //Save User Service

    /*
    * public void createUser(@RequestBody User user){
    *   User saveduser = service.saveUser(user);
    * }
    * */
    @PostMapping(path = "/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User saveduser = service.saveUser(user);

        //it will return current uri /users/saveduser.getID()
        URI location = ServletUriComponentsBuilder.
                 fromCurrentRequest().
                 path("/{id}").
                 buildAndExpand(saveduser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
