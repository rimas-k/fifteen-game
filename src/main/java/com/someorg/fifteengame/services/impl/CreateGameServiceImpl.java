package com.someorg.fifteengame.services.impl;

import com.someorg.fifteengame.domain.Game;
import com.someorg.fifteengame.domain.GameIdentifier;
import com.someorg.fifteengame.dto.CreateGameRequest;
import com.someorg.fifteengame.repositories.GameRepository;
import com.someorg.fifteengame.services.CreateGameService;
import com.someorg.fifteengame.services.util.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateGameServiceImpl implements CreateGameService {

    private GameRepository gameRepository;

    private DTOConverter dtoConverter;

    @Autowired
    public CreateGameServiceImpl(GameRepository gameRepository, DTOConverter dtoConverter) {
        this.gameRepository = gameRepository;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public com.someorg.fifteengame.dto.domain.Game createGame(CreateGameRequest request) {
        String userId = request.getUserId();
        String gameId = request.getGameId();
        int boardSize = request.getBoardSize();

        GameIdentifier gameIdentifier = new GameIdentifier(userId, gameId);
        Game game = gameRepository.createGame(gameIdentifier, boardSize);

        com.someorg.fifteengame.dto.domain.Game gameResponse = dtoConverter.createGameDTO(game);

        return gameResponse;
    }

}
