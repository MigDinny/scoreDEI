package com.example.demo;    

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.data.Team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service    
public class TeamService   
{    
    @Autowired    
    private TeamRepository teamRepository;

    public List<Team> getAllTeams()  
    {    
        List<Team>userRecords = new ArrayList<>();    
        teamRepository.findAll().forEach(userRecords::add);    
        return userRecords;    
    }

    public void addTeam(Team team)  
    {    
        teamRepository.save(team);    
    }

    public Optional<Team> getTeam(int id) {
        return teamRepository.findById(id);
    }

    /*
    public List<Team> findByNameEndsWith(String chars) {
        return teamRepository.findByNameEndsWith(chars);
    }*/

}    