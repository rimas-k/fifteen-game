package com.someorg.fifteengame.controllers;

import com.someorg.fifteengame.common.RegexpConstants;
import com.someorg.fifteengame.dto.CreateGameRequest;
import com.someorg.fifteengame.dto.FetchGameRequest;
import com.someorg.fifteengame.dto.MoveTileRequest;
import com.someorg.fifteengame.dto.MoveTileResponse;
import com.someorg.fifteengame.dto.domain.Game;
import com.someorg.fifteengame.services.CreateGameService;
import com.someorg.fifteengame.services.FetchGameService;
import com.someorg.fifteengame.services.MoveTileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
    public Game createGame(@RequestBody @Valid CreateGameRequest request) {
        return createGameService.createGame(request);
    }

    @GetMapping("/game/{userId}/{gameId}")
    public Game fetchGame(@PathVariable @NotBlank
                          @Pattern(regexp = RegexpConstants.ALPHANUMERIC) String userId,
                          @PathVariable @NotBlank
                          @Pattern(regexp = RegexpConstants.ALPHANUMERIC) String gameId) {
        FetchGameRequest request = new FetchGameRequest();
        request.setUserId(userId);
        request.setGameId(gameId);

        return fetchGameService.fetchGame(request);
    }

    @PostMapping("/game/move")
    public MoveTileResponse moveTile(@Valid @RequestBody MoveTileRequest request) {
        return moveTileService.moveTile(request);
    }

}
