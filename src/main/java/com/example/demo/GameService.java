package com.example.demo;

import java.util.List;
import java.util.Optional;

import java.util.ArrayList;    
import org.springframework.beans.factory.annotation.Autowired;    
import org.springframework.stereotype.Service;

import com.example.data.Game;

@Service   
public class GameService {

    @Autowired 
    private GameRepository gameRepository;


    public List<Game> getAllGames()  
    {    
        List<Game>userRecords = new ArrayList<>();    
        gameRepository.findAll().forEach(userRecords::add);    
        return userRecords;    
    }

    public void addGame(Game game)  
    {
        System.out.println(game);
        gameRepository.save(game);    
    }

    public Optional<Game> getGame(int id) {
        return gameRepository.findById(id);
    }

     //FALTAM FUNCOES COMO É OBVIO
}
