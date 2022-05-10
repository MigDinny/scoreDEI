package com.example.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "teamTable")
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;


    @OneToMany(mappedBy="team")
    private List<Player> players;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Game> games;

    public Team() {
    }

    public Team(String name) {
        this.name = name;
        this.players = new ArrayList<>();
        this.games = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player players) {
        this.players.add(players);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
