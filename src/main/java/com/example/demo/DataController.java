package com.example.demo;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Date;

//import com.example.data.Player;
import com.example.data.User;
import com.example.data.Team;
import com.example.data.Event;
import com.example.data.Game;
import com.example.data.Player;
import com.example.data.Role;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.example.core.ExternalREST;
import com.example.formdata.EventData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DataController {
    @Autowired
    PlayerService playerService;

    @Autowired
    TeamService teamService;

    @Autowired
    UserService userService;

    @Autowired
    GameService gameService;

    @Autowired
    EventService eventService;


    @Autowired
    RoleService roleService;
    

    @GetMapping("/")
    public String redirect() {
        if(roleService.getAllRoles().size() < 2){
            Role role1 = new Role("USER");
            Role role2 = new Role("ADMIN");
            roleService.addRole(role1);
            roleService.addRole(role2);
        }
        
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }


    // View of all the games
    @GetMapping("/viewGames")
    public String viewGames(Model m) {
        m.addAttribute("games", this.gameService.getAllGames());
        return "viewGames";
    }

    @GetMapping("/viewGames/viewEvents")
    public String viewEvents(@RequestParam(name="id", required=true) int id, Model m){
        Optional<Game> ga = this.gameService.getGame(id);
        if(ga.isPresent()){

            m.addAttribute("events", eventService.eventsOrdered(id));
            m.addAttribute("id", id);
            return "viewEvents";
        }
        return "redirect:/viewGames";
    }

    @GetMapping("/viewGames/addEvent")
    public String addEvent(@RequestParam(name="id", required=true) int id, Model m){
        Optional<Game> ga = this.gameService.getGame(id);
        System.out.println("HEREEEEE");
        if(ga.isPresent()){
            m.addAttribute("id", id);
            return "addEvent";
        }
        return "redirect:/viewGames";
    }
    
    @GetMapping("/viewGames/addEvent/startGame")
    public String startGame(@RequestParam(name="id", required = true) int id, Model m){
        Optional<Game> ga = this.gameService.getGame(id);
        if(ga.isPresent()){
            
            
            Game game = ga.get();
            game.setOngoing(true);
            game.setInterrupted(false);
            
            Event event = new Event("Game started");
            event.setDate(new Date());
            event.setGame(game);
            this.eventService.addEvent(event);
            game.addEvent(event);
            this.gameService.addGame(game);


        }
        return "redirect:/viewGames";
    }

    @GetMapping("/viewGames/addEvent/endGame")
    public String endGame(@RequestParam(name="id", required = true) int id, Model m){
        Optional<Game> ga = this.gameService.getGame(id);
        if(ga.isPresent() && ga.get().getOngoing()){

            Game game = ga.get();
            game.setOngoing(false);
            game.setInterrupted(true);

            Event event = new Event("Game ended");
            event.setDate(new Date());
            event.setGame(game);
            game.addEvent(event);


            this.eventService.addEvent(event);

            List<Team> teams = game.getTeams();
            Team team1 = teams.get(0);
            Team team2 = teams.get(1);

            int score1 = game.getScoreTeam1();
            int score2 = game.getScoreTeam2();

            if(score1 > score2){
                team1.setWins(team1.getWins()+1);
                team2.setLosses(team2.getLosses()+1);
            }
            else if(score2 > score1){
                team1.setLosses(team1.getLosses()+1);
                team2.setWins(team2.getWins()+1);
            }
            else{
                team1.setDraw(team1.getDraw()+1);
                team2.setDraw(team2.getDraw()+1);
            }
            this.teamService.addTeam(team1);
            this.teamService.addTeam(team2);
        }
        return "redirect:/viewGames";
    }

    @GetMapping("viewGames/addEvent/interruptGame")
    public String interruptGame(@RequestParam(name="id", required = true) int id, Model m){
        Optional<Game> ga = this.gameService.getGame(id);
        if(ga.isPresent() && ga.get().getOngoing()){
            Game game = ga.get();
            game.setInterrupted(true);

            Event event = new Event("Game interrupted");
            event.setDate(new Date());
            event.setGame(game);
            game.addEvent(event);

            this.eventService.addEvent(event);
        }
        return "redirect:/viewGames";
    }

    @GetMapping("viewGames/addEvent/resumeGame")
    public String resumeGame(@RequestParam(name="id", required = true) int id, Model m){
        Optional<Game> ga = this.gameService.getGame(id);
        if(ga.isPresent() && ga.get().getOngoing()){
            Game game = ga.get();
            game.setInterrupted(false);

            Event event = new Event("Game resumed");
            event.setDate(new Date());
            event.setGame(game);
            game.addEvent(event);

            this.eventService.addEvent(event);
        }
        return "redirect:/viewGames";
    }

    @GetMapping("viewGames/addEvent/newGoal")
    public String newGoal(@RequestParam(name="id", required = true) int id, Model m){
        Optional<Game> ga = this.gameService.getGame(id);
        if(ga.isPresent() && ga.get().getOngoing()){
            EventData event_d = new EventData(ga.get().getId(), " scored a Goal",3);
            m.addAttribute("event", event_d);

            Game game = ga.get();
            List<Player> players = new ArrayList<Player>();
            List<Team> teams = game.getTeams();

            Team team1 = teams.get(0);
            Team team2 = teams.get(1);

            players.addAll(team1.getPlayers());
            players.addAll(team2.getPlayers());

            m.addAttribute("players", players);

            return "addYellow";
        }
        return "redirect:/viewGames";
    }

    @GetMapping("viewGames/addEvent/newYellow")
    public String newYellow(@RequestParam(name="id", required = true) int id, Model m){

        Optional<Game> ga = this.gameService.getGame(id);
        if(ga.isPresent() && ga.get().getOngoing()){
            EventData event_d = new EventData(ga.get().getId(), " got a Yellow Card",1);
            m.addAttribute("event", event_d);

            Game game = ga.get();
            List<Player> players = new ArrayList<Player>();
            List<Team> teams = game.getTeams();

            Team team1 = teams.get(0);
            Team team2 = teams.get(1);

            players.addAll(team1.getPlayers());
            players.addAll(team2.getPlayers());

            m.addAttribute("players", players);
            return "addYellow";
        }
        return "redirect:/viewGames";
    }

    @GetMapping("viewGames/addEvent/newRed")
    public String newRed(@RequestParam(name="id", required = true) int id, Model m){
        Optional<Game> ga = this.gameService.getGame(id);
        if(ga.isPresent() && ga.get().getOngoing()){
            
            EventData event_d = new EventData(ga.get().getId(), " got a Red Card",2);
            m.addAttribute("event", event_d);

            Game game = ga.get();
            List<Player> players = new ArrayList<Player>();
            List<Team> teams = game.getTeams();

            Team team1 = teams.get(0);
            Team team2 = teams.get(1);

            players.addAll(team1.getPlayers());
            players.addAll(team2.getPlayers());

            m.addAttribute("players", players);
            return "addYellow";
        }
        return "redirect:/viewGames";
    }

    @PostMapping("viewGames/addEvent/submitEvent")
    public String submitEvent(@ModelAttribute EventData event){

        String description = "Player " + event.getPlayer().getName() + event.getDescription();
        
        Event newEvent = new Event(description);

        Optional<Game> ga = this.gameService.getGame(event.getGameId());
        if(ga.isPresent() && ga.get().getOngoing()){

            Game game = ga.get();
            
            Player player = event.getPlayer();

            //Yellow Card
            if(event.getType() == 1){
                player.setAmountYellows(player.getAmountYellows() +1);
            }
            //Red Card
            else if(event.getType() == 2){
                player.setAmountReds(player.getAmountReds() +1);
            }
            //Goal
            else if(event.getType() == 3){

                player.setAmountGoals(player.getAmountGoals() +1);
                Team team = player.getTeam();
                
                if(team.getId() == game.getTeams().get(0).getId()){
                    game.setScoreTeam1(game.getScoreTeam1()+1);
                }
                else if(team.getId() == game.getTeams().get(1).getId()) {
                    game.setScoreTeam2(game.getScoreTeam2()+1);
                }
                else{
                    return "redirect:/viewGames";
                }
    
            }

            newEvent.setGame(game);
            newEvent.setDate(event.getDate());

            this.eventService.addEvent(newEvent);

            game.addEvent(newEvent);
            this.gameService.addGame(game);
            this.playerService.addPlayer(player);
            

        }

        return "redirect:/viewGames";

    }

    @GetMapping("/statistics")
    public String statistics(Model m){
        m.addAttribute("wins", this.teamService.winsListOrdered());
        m.addAttribute("losses", this.teamService.lossesListOrdered());
        m.addAttribute("draws", this.teamService.drawListOrdered());
        m.addAttribute("best", this.playerService.findBestScorer());
        m.addAttribute("numberGames", this.teamService.numberGamesList());
        return "stats";
    }
    

    @GetMapping("/login")
    public String login(Model m){
        return "home";
    }
    
    @GetMapping("/logout")
    public String logout(Model m){
        return "logout";
    }


    @GetMapping("/admin/fill")
    public String fill(Model m) {
        // call REST API and save data on database


        ExternalREST er = new ExternalREST();
        int team_local_ids[] = new int[4];
        int team_remote_ids[] = new int[4];

        try {
            JSONObject teamsJSON = er.getTeams();

            JSONArray teamsArray = teamsJSON.getJSONArray("response");

            // int teams_number = teamsJSON.getInt("results");

            for (int i = 0; i < 4; i++) {
                JSONObject team = teamsArray.getJSONObject(i).getJSONObject("team");
                int teamID = team.getInt("id"); // id on the API
                String teamName = team.getString("name");

                Team newTeam = this.teamService.addTeam(new Team(teamName));
                int newTeamID = newTeam.getId(); // id on local database

                team_local_ids[i] = newTeamID;
                team_remote_ids[i] = teamID;

                //System.out.println(newTeamID + " - " + teamName);
            }

        } catch (UnirestException e) {
            e.printStackTrace();

            return "redirect:/home"; // or fillError
        }

        try {
            // for every team, query players on api with parameters and save them
            for (int i = 0; i < 4; i++) {

                JSONObject playersJSON = er.getPlayers(team_remote_ids[i]);

                JSONArray playersArray = playersJSON.getJSONArray("response");

                for (int u = 0; u < playersJSON.getInt("results"); u++) {
                    JSONObject playerEntry = playersArray.getJSONObject(u);

                    String name = playerEntry.getJSONObject("player").getString("name");
                    String position = playerEntry.getJSONArray("statistics").getJSONObject(0).getJSONObject("games").getString("position");
                    
                    Player newPlayer = new Player(name, position);
                    //System.out.println(playerEntry.getJSONObject("player").getJSONObject("birth").get("date"));
                    //System.out.println(playerEntry.getJSONObject("player").getJSONObject("birth").get("date") == null);

                    if (!playerEntry.getJSONObject("player").getJSONObject("birth").isNull("date")) {
                        String birthdate = playerEntry.getJSONObject("player").getJSONObject("birth").getString("date");
                        newPlayer.setBirthdate(new SimpleDateFormat("yyyy-MM-dd").parse(birthdate));
                    }

                    Optional<Team> t = this.teamService.getTeam(team_local_ids[i]);
                    
                    if (!t.isEmpty())
                        newPlayer.setTeam(t.get());

                    this.playerService.addPlayer(newPlayer);
                }

            }
        } catch (UnirestException e) {
            e.printStackTrace();
            return "redirect:/home"; // or fillError
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "redirect:/home"; // or fillError
    }

    @GetMapping("/admin/users")
    public String users(Model m) {
        m.addAttribute("us", this.userService.getAllUsers());
        m.addAttribute("user", new User());
        return "users";
    }

    @PostMapping("/admin/users/signup")
    public String usersSignup(@ModelAttribute("user") User user) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        if(userService.checkUserByName(user.getName())){
            return "redirect:/admin/users";
        }

        user.setPassword(encodedPassword);

        user.setEnabled(true);
        
        
        if(user.isAdmin()){
            Role role = roleService.getRoleByName("ADMIN").get(0);
            System.out.println(role);
            user.addRole(role);
        } else
            user.addRole(roleService.getRoleByName("USER").get(0));

        this.userService.addUser(user);

        return "redirect:/login";
    }

    @GetMapping("/admin/teams")
    public String teams(Model m) {
        m.addAttribute("allTeams", this.teamService.getAllTeams());
        m.addAttribute("team", new Team());
        return "teams";
    }

    @PostMapping("/admin/teams/create")
    public String teamsCreate(@ModelAttribute("team") Team team) {
        this.teamService.addTeam(team);
        return "redirect:/admin/teams";
    }

    @GetMapping("/admin/players")
    public String players(Model m) {
        m.addAttribute("allTeams", this.teamService.getAllTeams());
        m.addAttribute("allPlayers", this.playerService.getAllPlayers());
        m.addAttribute("player", new Player());
        return "players";
    }

    @PostMapping("/admin/players/create")
    public String playersCreate(@ModelAttribute("player") Player player) {
        // player.setTeam(this.teamService.getTeam(player.getTeamID()));
        this.playerService.addPlayer(player);

        return "redirect:/admin/players";
    }

    @GetMapping("admin/games")
    public String games(Model m) {
        m.addAttribute("allTeams", this.teamService.getAllTeams());
        m.addAttribute("game", new Game());
        return "games";
    }

    @PostMapping("/admin/games/create")
    public String gamesCreate(@ModelAttribute("game") Game game) {

        game.getTeams().get(0).addGame(game);
        game.getTeams().get(1).addGame(game);

        this.gameService.addGame(game);
        return "redirect:/admin/games";
    }

    @PostMapping("/submitNewEvent")
    public String newEvent(@ModelAttribute Event event) {
        this.eventService.addEvent(event);
        return "redirect:/home";
    }

}