package com.example.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import com.example.data.Event;

public interface EventRepository extends CrudRepository<Event, Integer>   
{ 
    @Query("select e from Event e WHERE game_id = :gameId ORDER BY (e.date) DESC")
    public List<Event> eventsOrdered(@Param("gameId") Integer gameId);

} 