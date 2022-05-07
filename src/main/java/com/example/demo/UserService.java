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


    public List<User> getAllEvents()  
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

    public Optional<User> getEvent(int id) {
        return userRepository.findById(id);
    }

     //FALTAM FUNCOES COMO É OBVIO
}
