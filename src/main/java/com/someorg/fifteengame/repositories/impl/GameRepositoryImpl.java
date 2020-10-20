package com.someorg.fifteengame.repositories.impl;

import com.someorg.fifteengame.common.MoveResult;
import com.someorg.fifteengame.factories.GameFactory;
import com.someorg.fifteengame.model.GameIdentifier;
import com.someorg.fifteengame.model.domain.Game;
import com.someorg.fifteengame.repositories.GameRepository;
import com.someorg.fifteengame.repositories.exceptions.GameAlreadyExistsException;
import com.someorg.fifteengame.repositories.exceptions.GameNotFoundException;
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
        this.GAMES_MAP = new ConcurrentHashMap<>();
        this.gameFactory = gameFactory;
    }

    @Override
    public synchronized Game createGame(GameIdentifier gameIdentifier, int boardSize) throws GameAlreadyExistsException {
        Map<String, Game> userGames = GAMES_MAP.get(gameIdentifier.getUserId());

        if (userGames == null) {
            userGames = new ConcurrentHashMap<>();
            GAMES_MAP.put(gameIdentifier.getUserId(), userGames);
        }

        if (userGames.containsKey(gameIdentifier.getGameId())) {
            throw new GameAlreadyExistsException("Game with specified ID already exists.");
        }

        Game game = gameFactory.createRandomGame(boardSize);
        userGames.put(gameIdentifier.getGameId(), game);

        return game;
    }

    @Override
    public Game fetchGame(GameIdentifier gameIdentifier) throws GameNotFoundException {
        Game game = null;
        Map<String, Game> userGames = GAMES_MAP.get(gameIdentifier.getUserId());

        if (userGames != null) {
            game = userGames.get(gameIdentifier.getGameId());
        }

        if (game == null) {
            throw new GameNotFoundException("Game with specified parameters was not found");
        }

        return game;
    }

    @Override
    public MoveResult moveTile(GameIdentifier gameIdentifier, String tileId) throws GameNotFoundException {
        Game game = fetchGame(gameIdentifier);
        MoveResult result = MoveResult.ILLEGAL_MOVE;

        if (game != null) {
            result = game.moveTileIntoBlankPosition(tileId);
        }

        return result;
    }
}
