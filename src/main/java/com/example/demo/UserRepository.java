package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import com.example.data.User;

public interface UserRepository extends CrudRepository<User, Integer>   
{ } 