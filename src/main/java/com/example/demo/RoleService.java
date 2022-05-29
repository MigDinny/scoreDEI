package com.example.demo;

import java.util.List;
import java.util.ArrayList;    
import org.springframework.beans.factory.annotation.Autowired;    
import org.springframework.stereotype.Service;

import com.example.data.Role;

@Service
public class RoleService {

    @Autowired 
    private RoleRepository roleRepository;

    public void addRole(Role role)  
    {
        roleRepository.save(role);    
    }

    public List<Role> getAllRoles(){
        List<Role>userRecords = new ArrayList<>();    
        roleRepository.findAll().forEach(userRecords::add);    
        return userRecords; 
    }

    public List<Role> getRoleByName(String roleName){
        return roleRepository.getRoleByName(roleName);
    }
}
