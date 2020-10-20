package com.someorg.fifteengame.controllers;

import com.someorg.fifteengame.dto.CreateGameRequest;
import com.someorg.fifteengame.dto.FetchGameRequest;
import com.someorg.fifteengame.dto.MoveTileRequest;
import com.someorg.fifteengame.dto.MoveTileResponse;
import com.someorg.fifteengame.dto.domain.Game;
import com.someorg.fifteengame.repositories.exceptions.GameAlreadyExistsException;
import com.someorg.fifteengame.repositories.exceptions.GameNotFoundException;
import com.someorg.fifteengame.services.CreateGameService;
import com.someorg.fifteengame.services.FetchGameService;
import com.someorg.fifteengame.services.MoveTileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@Validated
public class FifteenGameController {

    private CreateGameService createGameService;

    private FetchGameService fetchGameService;

    private MoveTileService moveTileService;

    @Autowired
    public FifteenGameController(CreateGameService createGameService, FetchGameService fetchGameService, MoveTileService moveTileService) {
        this.createGameService = createGameService;
        this.fetchGameService = fetchGameService;
        this.moveTileService = moveTileService;
    }

    @PostMapping("/game")
    public Game createGame(@RequestBody CreateGameRequest request) {
        try {
            return createGameService.createGame(request);
        } catch (GameAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/game/{userId}/{gameId}")
    public Game fetchGame(@PathVariable @NotBlank String userId, @PathVariable @NotBlank String gameId) {
        try {
            FetchGameRequest request = new FetchGameRequest();
            request.setUserId(userId);
            request.setGameId(gameId);

            return fetchGameService.fetchGame(request);
        } catch (GameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/game/move")
    public MoveTileResponse moveTile(@RequestBody MoveTileRequest request) {
        return moveTileService.moveTile(request);
    }

}
