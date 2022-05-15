package com.example.data;



import javax.persistence.*;



@Entity
@Table(name = "playerTable")
public class Player {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name, position;
    private int amountGoals, amount_yellows, amount_reds;
    //private TimeStamp date_game; Nao encontro o import e nao tou com paciencia para o encontrar xD
    @ManyToOne
    private Team team;

    public Player() {}

    public Player(String name, String position) {
        this.name = name;
        this.position = position;
        this.amountGoals = 0;
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
        return amountGoals;
    }

    public void setAmountGoals(int amoung_goals){
        this.amountGoals = amoung_goals;
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

    /**
     * @return Team return the team
     */
    public Team getTeam() {
        return team;
    }

    /**
     * @param team the team to set
     */
    public void setTeam(Team team) {
        this.team = team;
    }

}
