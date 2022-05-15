package com.example.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.data.Team;
import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Integer> {
    @Query("select t from Team t ORDER BY wins DESC")
    public List<Team> winsListOrdered();

    @Query("select t from Team t ORDER BY draw DESC")
    public List<Team> drawListOrdered();

    @Query("select t from Team t ORDER BY losses DESC")
    public List<Team> lossesListOrdered();

    @Query("select t from Team t ORDER BY (t.wins+t.draw+t.losses) DESC")
    public List<Team> numberGames();
}    