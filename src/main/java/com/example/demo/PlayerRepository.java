package com.example.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.data.Player;
import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Integer>   
{
    @Query("select p from Player p where amount_goals = (SELECT MAX(amountGoals) from Player)")
    public List<Player> findBestScorer();
} 