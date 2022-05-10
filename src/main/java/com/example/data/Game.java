package com.example.data;


import java.util.List;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Game {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int scoreTeam1;
    private int scoreTeam2;
    private int idWinner;
    private int idLooser;
    private String localization;
    private boolean interrupted;
    private boolean is_draw;
    //FALTA O CAMPO DA IMAGEM (BLOB)

    @OneToMany(mappedBy="game")
    private List<Event> events;

    @ManyToMany(mappedBy="games")
    private List<Team> teams;

    public Game() {
    }

    public Game(int idTeam1, int idTeam2, String localization) {

        this.scoreTeam1 = 0;
        this.scoreTeam2 = 0;
        this.localization = localization;
        this.interrupted = false;
        this.teams = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }


    public String getLocalization(){
        return localization;
    }

    public void setLocalization(String localization){
        this.localization = localization;
    }

    /**
     * @return int return the scoreTeam1
     */
    public int getScoreTeam1() {
        return scoreTeam1;
    }

    /**
     * @param scoreTeam1 the scoreTeam1 to set
     */
    public void setScoreTeam1(int scoreTeam1) {
        this.scoreTeam1 = scoreTeam1;
    }

    /**
     * @return int return the scoreTeam2
     */
    public int getScoreTeam2() {
        return scoreTeam2;
    }

    /**
     * @param scoreTeam2 the scoreTeam2 to set
     */
    public void setScoreTeam2(int scoreTeam2) {
        this.scoreTeam2 = scoreTeam2;
    }

    /**
     * @return int return the idWinner
     */
    public int getIdWinner() {
        return idWinner;
    }

    /**
     * @param idWinner the idWinner to set
     */
    public void setIdWinner(int idWinner) {
        this.idWinner = idWinner;
    }

    /**
     * @return int return the idLooser
     */
    public int getIdLooser() {
        return idLooser;
    }

    /**
     * @param idLooser the idLooser to set
     */
    public void setIdLooser(int idLooser) {
        this.idLooser = idLooser;
    }

    /**
     * @return boolean return the interrupted
     */
    public boolean isInterrupted() {
        return interrupted;
    }

    /**
     * @param interrupted the interrupted to set
     */
    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }

    /**
     * @return boolean return the is_draw
     */
    public boolean isIs_draw() {
        return is_draw;
    }

    /**
     * @param is_draw the is_draw to set
     */
    public void setIs_draw(boolean is_draw) {
        this.is_draw = is_draw;
    }

}
