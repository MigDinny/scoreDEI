package com.example.data;



import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;



@Entity
@Table(name = "playerTable")
public class Player {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name, position;
    private int amount_goals, amount_yellows, amount_reds;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;
    //private TimeStamp date_game; Nao encontro o import e nao tou com paciencia para o encontrar xD
    @ManyToOne(cascade = CascadeType.ALL)
    private Team team;
    
    public Player() {}


    public Player(String name, String position) {
        this.name = name;
        this.position = position;
        this.amount_goals = 0;
        this.amount_yellows = 0;
        this.amount_reds = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name + " (id = " + this.id + "). Office: " + this.position;
    }

    public int getAmountGoals(){
        return amount_goals;
    }

    public void setAmountGoals(int amoung_goals){
        this.amount_goals = amoung_goals;
    }

    public int getAmountYellows(){
        return amount_yellows;
    }

    public void setAmountYellows(int amount_yellows){
        this.amount_yellows = amount_yellows;
    }

    public int getAmountReds(){
        return amount_reds;
    }

    public void setAmountReds(int amount_reds){
        this.amount_reds = amount_reds;
    }
    
    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return this.team;
    }
}
