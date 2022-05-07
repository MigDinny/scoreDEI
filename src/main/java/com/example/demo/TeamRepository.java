package com.example.demo;

import org.springframework.data.repository.CrudRepository;


import com.example.data.Team;

public interface TeamRepository extends CrudRepository<Team, Integer> {
}    