package com.example.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import com.example.data.Role;


public interface RoleRepository extends CrudRepository<Role, Integer>   
{ 
    @Query("select r from Role r where name = ?1")
    public List<Role> getRoleByName(String chars);
} 