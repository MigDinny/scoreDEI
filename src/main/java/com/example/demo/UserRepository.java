package com.example.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import com.example.data.User;

public interface UserRepository extends CrudRepository<User, Integer>   
{ 
    @Query("SELECT u FROM User u WHERE u.name = :username")
    public User getUserByUsername(@Param("username") String username);

} 