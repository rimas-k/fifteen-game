package com.someorg.fifteengame.controllers;

import com.someorg.fifteengame.dto.*;
import com.someorg.fifteengame.dto.domain.Game;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FifteenGameController {

    @PostMapping("/game")
    public CreateGameResponse createGame(CreateGameRequest request){
        return new CreateGameResponse();
    }

    @GetMapping("/game")
    public Game fetchGame(FetchGameRequest request) {
        return new Game();
    }

    @PostMapping("/game/move")
    public MoveTileResponse moveTile(MoveTileRequest request) {
        return new MoveTileResponse();
    }

}
