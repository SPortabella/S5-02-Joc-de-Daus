package cat.itacademy.barcelonactiva.PORTABELLASUSANA.s05.t02.n01.f3.controller;

import cat.itacademy.barcelonactiva.PORTABELLASUSANA.s05.t02.n01.f3.exceptions.InvalidElementException;
import cat.itacademy.barcelonactiva.PORTABELLASUSANA.s05.t02.n01.f3.model.domain.Player;
import cat.itacademy.barcelonactiva.PORTABELLASUSANA.s05.t02.n01.f3.model.dto.RankingDTO;
import cat.itacademy.barcelonactiva.PORTABELLASUSANA.s05.t02.n01.f3.model.service.GameServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/dicegame/players")
public class GameController {

    GameServiceImpl gameService;

    public GameController(GameServiceImpl gameService) {
        this.gameService = gameService;
    }

    //TODO Player game
    @PostMapping("/{id}/games")
    public ResponseEntity<String> playGame(@PathVariable Integer id) {
        try {
            if (id == null) throw new InvalidElementException(Player.class);
            return ResponseEntity.ok().body(gameService.playGame(id).toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //TODO Get all single player games
    @GetMapping("/{id}/games")
    public ResponseEntity<String> getAllGames(@PathVariable Integer id) {
        try {
            if (id == null) throw new InvalidElementException(Player.class);
            List<Object> games = gameService.getAllGamesByIdPlayer(id);
            return ResponseEntity.ok().body(games.toString());
        } catch (Exception e) {
            List<Object> objectList = Collections.singletonList(new Object());
            objectList.add(e.getMessage());
            return (ResponseEntity.badRequest().body(objectList.toString()));
        }
    }

    //TODO Delete single player games
    @DeleteMapping("/{id}/games")
    public ResponseEntity<String> deleteGames(@PathVariable Integer id) {
        try {
            if (id == null) throw new InvalidElementException(Player.class);
            return ResponseEntity.ok().body(gameService.deleteGamesByIdPlayer(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //TODO Returns the average ranking of all players in the system. That is, the average percentage of successes.
    @GetMapping("/ranking")
    public ResponseEntity<String> getRankingGames() {
        try {
            List<RankingDTO> rankingGames = gameService.getRankingGames();
            if (rankingGames.size() == 0 || rankingGames.isEmpty())
                return ResponseEntity.ok().body("There aren't games");
            else
                return ResponseEntity.ok().body(rankingGames.toString());
        } catch (Exception e) {
            List<String> objectList = Collections.singletonList("");
            objectList.add(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //TODO Returns the player with the worst success rate.
    @GetMapping("/ranking/loser")
    public ResponseEntity<String> getRankingLoser() {
        try {
            String loser = gameService.getLoserRankingPlayer();
            return ResponseEntity.ok().body(loser.toString());
        } catch (Exception e) {
            List<String> objectList = Collections.singletonList("");
            objectList.add(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //TODO Returns the player with the best success rate.
    @GetMapping("/ranking/winner")
    public ResponseEntity<String> getRankingWinner() {
        try {
            String winner = gameService.getWinnerRankingPlayer();
            return ResponseEntity.ok().body(winner);
        } catch (Exception e) {
            List<String> objectList = Collections.singletonList("");
            objectList.add(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
