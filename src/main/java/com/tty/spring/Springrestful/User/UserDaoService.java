package com.tty.spring.Springrestful.User;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    private static int userCount = 3;
    static{
        users.add(new User(1,"Chris", new Date()));
        users.add(new User(2,"Albert", new Date()));
        users.add(new User(3,"Henry", new Date()));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User saveUser(User user){
        if(user.getId() == null){
            user.setId(++userCount);
        }
        users.add(user);
        return user;
    }

    public User getUser(int id){
        for(User user : users ){
            if(user.getId() == id){
                return user;
            }
        }

        return null;
    }

    public User DeleteById(int id){
        Iterator<User> iter = users.iterator();
        while(iter.hasNext()){
            User user = iter.next();
            if(user.getId() == id){
                iter.remove();
                return user;
            }
        }
        return null;
    }
}
