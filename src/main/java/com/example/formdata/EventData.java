package com.example.formdata;
import com.example.data.Game;
import com.example.data.Player;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;


public class EventData {
    private String description;
    private int gameId;
    //1-Yellow card 2-Red card, 3-Goal
    private int type;
    private Player player;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;


    public EventData() {
    }

    public EventData(int gameId, String description, int type){
        this.gameId = gameId;
        this.description = description;
        this.type = type;

    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGameId(){
        return gameId;
    }

    public void setGameId(int gameid){
        this.gameId = gameid;
    }

    public int getType(){
        return type;
    }

    public void setType(int type){
        this.type = type;
    }

    /**
     * @return Player return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return Date return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

}
