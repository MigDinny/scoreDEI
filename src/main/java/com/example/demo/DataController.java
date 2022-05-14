package com.example.demo;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Date;


//import com.example.data.Player;
import com.example.data.Team;
import com.example.data.User;
import com.example.data.Event;
import com.example.data.Game;
import com.example.data.Player;
import com.example.formdata.FormData;
import com.example.formdata.EventData;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.ModelAttribute;
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
    
    @GetMapping("/")
    public String redirect() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
    
    //PARA TESTES APAGAR NO FINAL
    @GetMapping("home/test")
    public String test(Model m){
        /*
        Event event = new Event("ola");
        Event event2 = new Event("wqeq");
        Game game = new Game(1,1, "Coimbra");

        List<Event> events = new ArrayList<Event>();
        events.add(event);
        events.add(event2);
        event.setGame(game);
        m.addAttribute("events", events);
        m.addAttribute("id", 1);
        Team team1 = new Team("sporting");
        Team team2 = new Team("brah");
        Team team3 = new Team("mirandense");

        
        Game game2 = new Game(1,1, "Acores");

        game.addTeams(team1);
        game.addTeams(team2);

        game2.addTeams(team2);
        game2.addTeams(team3);

        game.setDate(new Date());
        game2.setDate(new Date());
        List<Game> games = new ArrayList<Game>();
        games.add(game);
        games.add(game2);
        m.addAttribute("games", games);*/
        Game game = new Game("Coimbra");
        game.setDate(new Date());


        Player player = new Player("Edgar", "striker");
        Player player2 = new Player("Mijel", "goalkeeper");
        Player player3 = new Player("Afonso", "striker");
        Team team1 = new Team("sporting");
        Team team2 = new Team("brah");
        Team team3 = new Team("asd");

        player.setTeam(team1);
        player2.setTeam(team2);
        player3.setTeam(team3);

        team1.addGame(game);
        team2.addGame(game);

        game.addTeams(team1);
        game.addTeams(team2);

        this.gameService.addGame(game);
        this.teamService.addTeam(team1);
        this.teamService.addTeam(team2);
        this.teamService.addTeam(team3);
        this.playerService.addPlayer((player));
        this.playerService.addPlayer((player2));    
        this.playerService.addPlayer((player3));

        EventData event_d = new EventData(game.getId(), "Yellow Card",1);

        m.addAttribute("event", event_d);
        m.addAttribute("players", this.playerService.getAllPlayers());
        return "addYellow";

    }

    //View of all the games
    @GetMapping("/viewGames")
    public String viewGames(Model m){
        m.addAttribute("games", this.gameService.getAllGames());
        return "viewGames";
    }

    @GetMapping("/viewGames/viewEvents")
    public String viewEvents(@RequestParam(name="id", required=true) int id, Model m){
        Optional<Game> ga = this.gameService.getGame(id);
        if(ga.isPresent()){

            m.addAttribute("events", ga.get().getEvents());
            m.addAttribute("id", id);
            return "viewEvents";
        }
        return "redirect:/viewGames";
    }

    @GetMapping("viewGames/addEvent")
    public String addEvent(@RequestParam(name="id", required=true) int id, Model m){
        Optional<Game> ga = this.gameService.getGame(id);
        if(ga.isPresent()){
            m.addAttribute("id", id);
            return "addEvent";
        }
        return "redirect:/viewGames";
    }
    
    @GetMapping("viewGames/addEvent/startGame")
    public String startGame(@RequestParam(name="id", required = true) int id, Model m){
        Optional<Game> ga = this.gameService.getGame(id);
        if(ga.isPresent()){
            
            
            Game game = ga.get();
            game.setOngoing(true);
            game.setInterrupted(false);
            
            Event event = new Event("Game started");

            event.setGame(game);
            this.eventService.addEvent(event);
            game.addEvent(event);
            this.gameService.addGame(game);


        }
        return "redirect:/viewGames";
    }

    @GetMapping("viewGames/addEvent/endGame")
    public String endGame(@RequestParam(name="id", required = true) int id, Model m){
        Optional<Game> ga = this.gameService.getGame(id);
        if(ga.isPresent()){

            Game game = ga.get();
            game.setOngoing(false);
            game.setInterrupted(true);

            Event event = new Event("Game ended");
            game.addEvent(event);

            this.eventService.addEvent(event);
        }
        return "redirect:/viewGames";
    }

    @GetMapping("viewGames/addEvent/interruptGame")
    public String interruptGame(@RequestParam(name="id", required = true) int id, Model m){
        Optional<Game> ga = this.gameService.getGame(id);
        if(ga.isPresent()){
            Game game = ga.get();
            game.setInterrupted(true);

            Event event = new Event("Game interrupted");
            game.addEvent(event);

            this.eventService.addEvent(event);
        }
        return "redirect:/viewGames";
    }

    @GetMapping("viewGames/addEvent/resumeGame")
    public String resumeGame(@RequestParam(name="id", required = true) int id, Model m){
        Optional<Game> ga = this.gameService.getGame(id);
        if(ga.isPresent()){
            Game game = ga.get();
            game.setInterrupted(false);

            Event event = new Event("Game resumed");
            game.addEvent(event);

            this.eventService.addEvent(event);
        }
        return "redirect:/viewGames";
    }

    @GetMapping("viewGames/addEvent/newGoal")
    public String newGoal(@RequestParam(name="id", required = true) int id, Model m){
        Optional<Game> ga = this.gameService.getGame(id);
        if(ga.isPresent()){
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
        if(ga.isPresent()){
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
        if(ga.isPresent()){
            
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

    @GetMapping("viewGames/addEvent/submitEvent")
    public String submitEvent(@ModelAttribute EventData event){

        String description = "Player " + event.getPlayer().getName() + event.getDescription();
        
        Event newEvent = new Event(description);

        Optional<Game> ga = this.gameService.getGame(event.getGameId());
        if(ga.isPresent()){

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
            

        }

        return "redirect:/viewGames";

    }


    /*
    @GetMapping("/queryStudents")
    public String queryStudent1(Model m) {
        m.addAttribute("person", new FormData());
        return "queryStudents";
    }

    // Note the invocation of a service method that is served by a query in jpql 

    @GetMapping("/queryResults")
    public String queryResult1(@ModelAttribute FormData data, Model m) {
        List<Team> ls = this.studentService.findByNameEndsWith(data.getName());
        m.addAttribute("students", ls);
        return "listStudents";
    }

    @GetMapping("/listProfessors")
    public String listProfs(Model model) {
        model.addAttribute("professors", this.profService.getAllProfessors());
        return "listProfessors";
    }

    @GetMapping("/createProfessor")
    public String createProfessor(Model m) {
        m.addAttribute("professor", new Professor());
        return "editProfessor";
    }

    

    

    @PostMapping("/saveProfessor")
    public String saveProfessor(@ModelAttribute Professor prof) {
        this.profService.addProfessor(prof);
        return "redirect:/listProfessors";
    }
    
    */
    //@PostMapping("/sumbitOfficeChange")
    //public String changeOffice(@ModelAttribute Professor prof) {
    //    this.profService.changeProfOffice(prof.getId(), prof.getOffice());
    //    return "redirect:/listProfessors";
    //}
    //private String getEditProfessorForm(int id, String formName, Model m) {
    //    Optional<Professor> op = this.profService.getProfessor(id);
    //    if (op.isPresent()) {
    //        m.addAttribute("professor", op.get());
    //        return formName;
    //    }
    //    return "redirect:/listProfessors";
    //}

    //@GetMapping("/editProfessor")
    //public String editProfessor(@RequestParam(name="id", required=true) int id, Model m) {
    //    return getEditProfessorForm(id, "editProfessor", m);
    //}    

    // For the sake of illustrating the use of @Transactional 
    //@GetMapping("/changeOffice")
    //public String getOfficeForm(@RequestParam(name="id", required=true) int id, Model m) {
    //    return getEditProfessorForm(id, "editProfessorOffice", m);
    //}

    //TUDO VAI SER REESCRITO
    /*
    @GetMapping("/createData")
    public String createData() {
        return "createData";
    }

	@PostMapping("/saveData")
	public String saveData(Model model) {
        Professor[] myprofs = { 
            new Professor("José", "D3.1"), 
            new Professor("Paulo", "135"), 
            new Professor("Estrela", "180")
        };
        Team[] mystudents = { 
            new Team("Paula", "91999991", 21),
            new Team("Artur", "91999992", 21),
            new Team("Rui", "91999993", 19),
            new Team("Luísa", "91999994", 20),
            new Team("Alexandra", "91999995", 21),
            new Team("Carlos", "91999995", 22)
        };

        mystudents[0].addProf(myprofs[0]);
        mystudents[0].addProf(myprofs[1]);
        mystudents[1].addProf(myprofs[1]);
        mystudents[1].addProf(myprofs[2]);
        mystudents[2].addProf(myprofs[0]);
        mystudents[3].addProf(myprofs[2]);
        mystudents[4].addProf(myprofs[1]);
        mystudents[5].addProf(myprofs[0]);
        mystudents[5].addProf(myprofs[1]);
        mystudents[5].addProf(myprofs[2]);

        for (Team s : mystudents)
            this.studentService.addStudent(s);
    
		return "redirect:/listStudents";
	}

    @GetMapping("/listStudents")
    public String listStudents(Model model) {
        model.addAttribute("students", this.studentService.getAllStudents());
        return "listStudents";
    }

    @GetMapping("/createStudent")
    public String createStudent(Model m) {
        m.addAttribute("student", new Team());
        m.addAttribute("allProfessors", this.profService.getAllProfessors());
        return "editStudent";
    }

    @GetMapping("/editStudent")
    public String editStudent(@RequestParam(name="id", required=true) int id, Model m) {
        Optional<Team> op = this.studentService.getStudent(id);
        if (op.isPresent()) {
            m.addAttribute("student", op.get());
            m.addAttribute("allProfessors", this.profService.getAllProfessors());
            return "editStudent";
        }
        else {
            return "redirect:/listStudents";
        }
    }    

    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute Team st) {
        this.studentService.addStudent(st);
        return "redirect:/listStudents";
    }

   

    

    */

}