package com.someorg.fifteengame.controllers;

import com.someorg.fifteengame.dto.*;
import com.someorg.fifteengame.dto.domain.Game;
import com.someorg.fifteengame.services.CreateGameService;
import com.someorg.fifteengame.services.FetchGameService;
import com.someorg.fifteengame.services.MoveTileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
    public CreateGameResponse createGame(CreateGameRequest request) {
        return createGameService.createGame(request);
    }

    @GetMapping("/game")
    public Game fetchGame(FetchGameRequest request) {
        return fetchGameService.fetchGame(request);
    }

    @PostMapping("/game/move")
    public MoveTileResponse moveTile(MoveTileRequest request) {
        return moveTileService.moveTile(request);
    }

}
