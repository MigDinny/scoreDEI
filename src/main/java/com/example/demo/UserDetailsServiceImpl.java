package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;


import com.example.data.User;

//Adaptado de https://www.codejava.net/frameworks/spring-boot/spring-boot-security-role-based-authorization-tutorial
public class UserDetailsServiceImpl implements UserDetailsService  {
    @Autowired
    private UserRepository userRepository;
     
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<User> user = userRepository.getUserByUsername(username);

        User userR = user.get();
         
        if (userR == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new MyUserDetails(userR);
    }
}
