package com.someorg.fifteengame.services.impl;

import com.someorg.fifteengame.common.MoveResult;
import com.someorg.fifteengame.model.GameIdentifier;
import com.someorg.fifteengame.model.domain.Game;
import com.someorg.fifteengame.services.GameRepository;
import com.someorg.fifteengame.services.exceptions.GameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameRepositoryImpl implements GameRepository {

    private final Map<String, Map<String, Game>> GAMES_MAP;

    private GameFactory gameFactory;

    @Autowired
    public GameRepositoryImpl(GameFactory gameFactory) {
        GAMES_MAP = new ConcurrentHashMap<>();
        this.gameFactory = gameFactory;
    }

    @Override
    public Game createGame(GameIdentifier gameIdentifier, int boardSize) throws GameAlreadyExistsException {
        Map<String, Game> userGames = GAMES_MAP.get(gameIdentifier.getUserId());

        if (userGames == null) {
            userGames = new ConcurrentHashMap<>();
            GAMES_MAP.put(gameIdentifier.getUserId(), userGames);
        }

        if(userGames.containsKey(gameIdentifier.getGameId())) {
            throw new GameAlreadyExistsException("Game with specified ID already exists.");
        }

        Game game = gameFactory.createRandomGame(boardSize);
        userGames.put(gameIdentifier.getGameId(), game);

        return game;
    }

    @Override
    public Game fetchGame(GameIdentifier gameIdentifier) {
        return null;
    }

    @Override
    public MoveResult moveTile(GameIdentifier gameIdentifier, String tileId) {
        return null;
    }
}
