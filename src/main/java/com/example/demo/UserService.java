package com.example.demo;

import java.util.List;
import java.util.Optional;

import java.util.ArrayList;    
import org.springframework.beans.factory.annotation.Autowired;    
import org.springframework.stereotype.Service;

import com.example.data.User;

@Service   
public class UserService {

    @Autowired 
    private UserRepository userRepository;


    public List<User> getAllUsers()  
    {    
        List<User>userRecords = new ArrayList<>();    
        userRepository.findAll().forEach(userRecords::add);    
        return userRecords;    
    }

    public void addUser(User user)  
    {
        System.out.println(user);
        userRepository.save(user);    
    }

    public Optional<User> getUser(int id) {
        return userRepository.findById(id);
    }

    public boolean checkUserByName(String userName){
        Optional<User> user =userRepository.getUserByUsername(userName);

        if(user.isPresent()){
            return true;
        }
        return false;
    }

}
