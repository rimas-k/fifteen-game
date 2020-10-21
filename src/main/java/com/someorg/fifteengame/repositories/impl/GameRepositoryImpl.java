package com.someorg.fifteengame.repositories.impl;

import com.someorg.fifteengame.common.MoveResult;
import com.someorg.fifteengame.factories.GameFactory;
import com.someorg.fifteengame.model.GameIdentifier;
import com.someorg.fifteengame.model.Game;
import com.someorg.fifteengame.repositories.GameRepository;
import com.someorg.fifteengame.repositories.exceptions.GameAlreadyExistsException;
import com.someorg.fifteengame.repositories.exceptions.GameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameRepositoryImpl implements GameRepository {

    private final Map<GameIdentifier, Game> GAMES_MAP;

    private GameFactory gameFactory;

    @Autowired
    public GameRepositoryImpl(GameFactory gameFactory) {
        this.GAMES_MAP = new ConcurrentHashMap<>();
        this.gameFactory = gameFactory;
    }

    @Override
    public synchronized Game createGame(GameIdentifier gameIdentifier, int boardSize) {
        if (GAMES_MAP.containsKey(gameIdentifier)) {
            throw new GameAlreadyExistsException("Game with specified ID already exists.");
        }

        Game game = gameFactory.createRandomGame(boardSize);
        GAMES_MAP.put(gameIdentifier, game);

        return game;
    }

    @Override
    public Game fetchGame(GameIdentifier gameIdentifier) {
        Game game = GAMES_MAP.get(gameIdentifier);

        if (game == null) {
            throw new GameNotFoundException("Game with specified identifier was not found");
        }

        return game;
    }

    @Override
    public MoveResult moveTile(GameIdentifier gameIdentifier, String tileLabel) {
        Game game = fetchGame(gameIdentifier);
        MoveResult result = MoveResult.ILLEGAL_MOVE;

        if (game != null) {
            result = game.moveTileIntoBlankPosition(tileLabel);
        }

        return result;
    }
}
