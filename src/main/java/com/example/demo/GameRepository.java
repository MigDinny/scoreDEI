package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import com.example.data.Game;

public interface GameRepository extends CrudRepository<Game, Integer>   
{ } 