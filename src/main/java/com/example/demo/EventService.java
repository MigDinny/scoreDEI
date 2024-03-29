package com.example.demo;

import java.util.List;
import java.util.Optional;

import java.util.ArrayList;    
import org.springframework.beans.factory.annotation.Autowired;    
import org.springframework.stereotype.Service;

import com.example.data.Event;

@Service   
public class EventService {

    @Autowired 
    private EventRepository eventRepository;


    public List<Event> getAllEvents()  
    {    
        List<Event>userRecords = new ArrayList<>();    
        eventRepository.findAll().forEach(userRecords::add);    
        return userRecords;    
    }

    public void addEvent(Event event)  
    {
        eventRepository.save(event);    
    }

    public Optional<Event> getEvent(int id) {
        return eventRepository.findById(id);
    }

    public List<Event> eventsOrdered(int id){
        return eventRepository.eventsOrdered(id);
    }


}
