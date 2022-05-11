package com.example.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import java.sql.Blob;
import javax.sql.rowset.serial.SerialBlob;

@Entity
@Table(name = "teamTable")
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private Blob blob;


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

    /**
     * @return List<Game> return the games
     */
    public List<Game> getGames() {
        return games;
    }

    /**
     * @param games the games to set
     */
    public void setGames(List<Game> games) {
        this.games = games;
    }

    public void addGame(Game game) {
        this.games.add(game);
    }


    /**
     * @return Blob return the blob
     */
    public Blob getBlob() {
        return blob;
    }

    /**
     * @param blob the blob to set
     */
    public void setBlob(Blob blob) {
        this.blob = blob;
    }

}
