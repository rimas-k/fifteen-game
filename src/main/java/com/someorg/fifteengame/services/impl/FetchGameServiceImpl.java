package com.someorg.fifteengame.services.impl;

import com.someorg.fifteengame.dto.FetchGameRequest;
import com.someorg.fifteengame.dto.domain.Game;
import com.someorg.fifteengame.model.GameIdentifier;
import com.someorg.fifteengame.repositories.GameRepository;
import com.someorg.fifteengame.repositories.exceptions.GameNotFoundException;
import com.someorg.fifteengame.services.FetchGameService;
import com.someorg.fifteengame.services.util.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FetchGameServiceImpl implements FetchGameService {

    private GameRepository gameRepository;

    private DTOConverter dtoConverter;

    @Autowired
    public FetchGameServiceImpl(GameRepository gameRepository, DTOConverter dtoConverter) {
        this.gameRepository = gameRepository;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public Game fetchGame(FetchGameRequest request) throws GameNotFoundException {
        String userId = request.getUserId();
        String gameId = request.getGameId();

        GameIdentifier gameIdentifier = new GameIdentifier(userId, gameId);

        com.someorg.fifteengame.model.domain.Game game = gameRepository.fetchGame(gameIdentifier);

        Game gameDTO = dtoConverter.createGameDTO(game);

        return gameDTO;
    }
}
