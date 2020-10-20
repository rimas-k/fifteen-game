package com.someorg.fifteengame.services.impl;

import com.someorg.fifteengame.common.MoveResult;
import com.someorg.fifteengame.dto.MoveTileRequest;
import com.someorg.fifteengame.dto.MoveTileResponse;
import com.someorg.fifteengame.model.GameIdentifier;
import com.someorg.fifteengame.repositories.GameRepository;
import com.someorg.fifteengame.services.MoveTileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MoveTileServiceImpl implements MoveTileService {

    private GameRepository gameRepository;

    @Autowired
    public MoveTileServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public MoveTileResponse moveTile(MoveTileRequest request) {
        String tileLabel = request.getTileLabel();
        String userId = request.getUserId();
        String gameId = request.getGameId();

        GameIdentifier gameIdentifier = new GameIdentifier(userId, gameId);

        MoveResult moveResult = gameRepository.moveTile(gameIdentifier, tileLabel);

        MoveTileResponse response = new MoveTileResponse();
        response.setResult(moveResult);

        return response;
    }
}
