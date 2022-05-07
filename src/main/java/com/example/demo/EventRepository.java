package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import com.example.data.Event;

public interface EventRepository extends CrudRepository<Event, Integer>   
{ } 