package com.example.core;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.*;

public class ExternalREST {

    private final static String URL = "https://v3.football.api-sports.io/";
    private final static String TEAMS_ENDPOINT = "teams";
    private final static String PLAYERS_ENDPOINT = "players";
    private final static String API_KEY = "a09d68f9818785f2cd402e7640250991";

    public ExternalREST() {
        Unirest.setTimeouts(0, 0);
    }

    private static JSONObject get(String endpoint, String params) throws UnirestException {
        HttpResponse<String> response;
        response = Unirest.get(URL + endpoint + params)
                .header("x-rapidapi-key", API_KEY)
                .header("x-rapidapi-host", "v3.football.api-sports.io")
                .asString();

        //System.out.println(response.getStatus());
        //System.out.println(response.getBody());

        JSONObject jo = new JSONObject(response.getBody());

        return jo;
    }

    public JSONObject getTeams() throws UnirestException {
        return get(TEAMS_ENDPOINT, "?league=39&season=2021");
    }

    public JSONObject getPlayers(int teamID) throws UnirestException {
        return get(PLAYERS_ENDPOINT, "?team=" + teamID + "&season=2021");
    }

}
