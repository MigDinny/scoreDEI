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
import com.example.formdata.FormData;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.example.core.ExternalREST;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/")
    public String redirect() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    // PARA TESTES APAGAR NO FINAL
    @GetMapping("/test")
    public String test(Model m) {
        /*
         * Event event = new Event("ola");
         * Event event2 = new Event("wqeq");
         * Game game = new Game(1,1, "Coimbra");
         * 
         * List<Event> events = new ArrayList<Event>();
         * events.add(event);
         * events.add(event2);
         * event.setGame(game);
         * m.addAttribute("events", events);
         * m.addAttribute("id", 1);
         * Team team1 = new Team("sporting");
         * Team team2 = new Team("brah");
         * Team team3 = new Team("mirandense");
         * 
         * Game game = new Game(1,1, "Coimbra");
         * Game game2 = new Game(1,1, "Acores");
         * 
         * game.addTeams(team1);
         * game.addTeams(team2);
         * 
         * game2.addTeams(team2);
         * game2.addTeams(team3);
         * 
         * game.setDate(new Date());
         * game2.setDate(new Date());
         * List<Game> games = new ArrayList<Game>();
         * games.add(game);
         * games.add(game2);
         * m.addAttribute("games", games);
         */
        return "home";
    }

    // View of all the games
    @GetMapping("/viewGames")
    public String viewGames(Model m) {
        m.addAttribute("games", this.gameService.getAllGames());
        return "viewGames";
    }

    @GetMapping("/viewEvents")
    public String viewEvents(@RequestParam(name = "id", required = true) int id, Model m) {
        Optional<Game> ga = this.gameService.getGame(id);
        if (ga.isPresent()) {
            m.addAttribute("events", ga.get().getEvents());
            m.addAttribute("id", id);
            return "viewEvents";
        }
        return "redirect:/viewGames";
    }

    @GetMapping("/addEvent")
    public String addEvent(@RequestParam(name = "id", required = true) int id, Model m) {
        Optional<Game> ga = this.gameService.getGame(id);
        if (ga.isPresent()) {
            m.addAttribute("id", id);
            return "addEvent";
        }
        return "redirect:/home";
    }

    @GetMapping("/startGame")
    public String startGame(@RequestParam(name = "id", required = true) int id, Model m) {
        Optional<Game> ga = this.gameService.getGame(id);
        if (ga.isPresent()) {

            Game game = ga.get();
            game.setOngoing(true);

            Event event = new Event("Game started");
            game.addEvent(event);

            this.eventService.addEvent(event);
        }
        return "redirect:/home";
    }

    @GetMapping("/endGame")
    public String endGame(@RequestParam(name = "id", required = true) int id, Model m) {
        Optional<Game> ga = this.gameService.getGame(id);
        if (ga.isPresent()) {

            Game game = ga.get();
            game.setOngoing(false);

            Event event = new Event("Game ended");
            game.addEvent(event);

            this.eventService.addEvent(event);
        }
        return "redirect:/home";
    }

    @GetMapping("/newGoal")
    public String newGoal(@RequestParam(name = "id", required = true) int id, Model m) {
        Optional<Game> ga = this.gameService.getGame(id);
        if (ga.isPresent()) {
            m.addAttribute("g", id);
            return "addGoal";
        }
        return "redirect:/home";
    }

    @GetMapping("/newYellow")
    public String newYellow(@RequestParam(name = "id", required = true) int id, Model m) {
        Optional<Game> ga = this.gameService.getGame(id);
        if (ga.isPresent()) {
            m.addAttribute("g", id);
            return "addYellow";
        }
        return "redirect:/home";
    }

    @GetMapping("/newRed")
    public String newRed(@RequestParam(name = "id", required = true) int id, Model m) {
        Optional<Game> ga = this.gameService.getGame(id);
        if (ga.isPresent()) {
            m.addAttribute("g", id);
            return "addRed";
        }
        return "redirect:/home";
    }

    @GetMapping("/interruptGame")
    public String interruptGame(@RequestParam(name = "id", required = true) int id, Model m) {
        Optional<Game> ga = this.gameService.getGame(id);
        if (ga.isPresent()) {
            Game game = ga.get();
            game.setInterrupted(true);
        }
        return "redirect:/home";
    }

    @GetMapping("/resumeGame")
    public String resumeGame(@RequestParam(name = "id", required = true) int id, Model m) {
        Optional<Game> ga = this.gameService.getGame(id);
        if (ga.isPresent()) {
            Game game = ga.get();
            game.setInterrupted(false);
        }
        return "redirect:/home";
    }

    // @PostMapping("/sumbitOfficeChange")
    // public String changeOffice(@ModelAttribute Professor prof) {
    // this.profService.changeProfOffice(prof.getId(), prof.getOffice());
    // return "redirect:/listProfessors";
    // }
    // private String getEditProfessorForm(int id, String formName, Model m) {
    // Optional<Professor> op = this.profService.getProfessor(id);
    // if (op.isPresent()) {
    // m.addAttribute("professor", op.get());
    // return formName;
    // }
    // return "redirect:/listProfessors";
    // }

    // @GetMapping("/editProfessor")
    // public String editProfessor(@RequestParam(name="id", required=true) int id,
    // Model m) {
    // return getEditProfessorForm(id, "editProfessor", m);
    // }

    // For the sake of illustrating the use of @Transactional
    // @GetMapping("/changeOffice")
    // public String getOfficeForm(@RequestParam(name="id", required=true) int id,
    // Model m) {
    // return getEditProfessorForm(id, "editProfessorOffice", m);
    // }

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
        this.userService.addUser(user);

        return "redirect:/admin/users";
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

    // TUDO VAI SER REESCRITO
    /*
     * @GetMapping("/createData")
     * public String createData() {
     * return "createData";
     * }
     * 
     * @PostMapping("/saveData")
     * public String saveData(Model model) {
     * Professor[] myprofs = {
     * new Professor("José", "D3.1"),
     * new Professor("Paulo", "135"),
     * new Professor("Estrela", "180")
     * };
     * Team[] mystudents = {
     * new Team("Paula", "91999991", 21),
     * new Team("Artur", "91999992", 21),
     * new Team("Rui", "91999993", 19),
     * new Team("Luísa", "91999994", 20),
     * new Team("Alexandra", "91999995", 21),
     * new Team("Carlos", "91999995", 22)
     * };
     * 
     * mystudents[0].addProf(myprofs[0]);
     * mystudents[0].addProf(myprofs[1]);
     * mystudents[1].addProf(myprofs[1]);
     * mystudents[1].addProf(myprofs[2]);
     * mystudents[2].addProf(myprofs[0]);
     * mystudents[3].addProf(myprofs[2]);
     * mystudents[4].addProf(myprofs[1]);
     * mystudents[5].addProf(myprofs[0]);
     * mystudents[5].addProf(myprofs[1]);
     * mystudents[5].addProf(myprofs[2]);
     * 
     * for (Team s : mystudents)
     * this.studentService.addStudent(s);
     * 
     * return "redirect:/listStudents";
     * }
     * 
     * @GetMapping("/listStudents")
     * public String listStudents(Model model) {
     * model.addAttribute("students", this.studentService.getAllStudents());
     * return "listStudents";
     * }
     * 
     * @GetMapping("/createStudent")
     * public String createStudent(Model m) {
     * m.addAttribute("student", new Team());
     * m.addAttribute("allProfessors", this.profService.getAllProfessors());
     * return "editStudent";
     * }
     * 
     * @GetMapping("/editStudent")
     * public String editStudent(@RequestParam(name="id", required=true) int id,
     * Model m) {
     * Optional<Team> op = this.studentService.getStudent(id);
     * if (op.isPresent()) {
     * m.addAttribute("student", op.get());
     * m.addAttribute("allProfessors", this.profService.getAllProfessors());
     * return "editStudent";
     * }
     * else {
     * return "redirect:/listStudents";
     * }
     * }
     * 
     * @PostMapping("/saveStudent")
     * public String saveStudent(@ModelAttribute Team st) {
     * this.studentService.addStudent(st);
     * return "redirect:/listStudents";
     * }
     * 
     * @GetMapping("/queryStudents")
     * public String queryStudent1(Model m) {
     * m.addAttribute("person", new FormData());
     * return "queryStudents";
     * }
     * 
     * // Note the invocation of a service method that is served by a query in jpql
     * 
     * @GetMapping("/queryResults")
     * public String queryResult1(@ModelAttribute FormData data, Model m) {
     * List<Team> ls = this.studentService.findByNameEndsWith(data.getName());
     * m.addAttribute("students", ls);
     * return "listStudents";
     * }
     * 
     * @GetMapping("/listProfessors")
     * public String listProfs(Model model) {
     * model.addAttribute("professors", this.profService.getAllProfessors());
     * return "listProfessors";
     * }
     * 
     * @GetMapping("/createProfessor")
     * public String createProfessor(Model m) {
     * m.addAttribute("professor", new Professor());
     * return "editProfessor";
     * }
     * 
     * 
     * 
     * 
     * 
     * @PostMapping("/saveProfessor")
     * public String saveProfessor(@ModelAttribute Professor prof) {
     * this.profService.addProfessor(prof);
     * return "redirect:/listProfessors";
     * }
     * 
     * 
     * 
     */

}