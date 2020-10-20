package com.someorg.fifteengame.repositories;

import com.someorg.fifteengame.common.MoveResult;
import com.someorg.fifteengame.model.GameIdentifier;
import com.someorg.fifteengame.model.domain.Game;
import com.someorg.fifteengame.repositories.exceptions.GameAlreadyExistsException;

public interface GameRepository {

    Game createGame(GameIdentifier gameIdentifier, int boardSize) throws GameAlreadyExistsException;

    Game fetchGame(GameIdentifier gameIdentifier);

    MoveResult moveTile(GameIdentifier gameIdentifier, String tileId);

}
