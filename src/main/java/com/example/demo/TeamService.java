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

    public Team addTeam(Team team)  
    {    
        return teamRepository.save(team);   
    }

    public Optional<Team> getTeam(int id) {
        return teamRepository.findById(id);
    }

    public List<Team> lossesListOrdered(){
        return teamRepository.lossesListOrdered();
    }

    public List<Team> winsListOrdered(){
        return teamRepository.winsListOrdered();
    }

    public List<Team> drawListOrdered(){
        return teamRepository.drawListOrdered();
    }

    public List<Team> numberGamesList(){
        return teamRepository.numberGames();
    }


}    