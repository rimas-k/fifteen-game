package com.someorg.fifteengame.repositories;

import com.someorg.fifteengame.common.MoveResult;
import com.someorg.fifteengame.model.GameIdentifier;
import com.someorg.fifteengame.model.Game;

public interface GameRepository {

    Game createGame(GameIdentifier gameIdentifier, int boardSize);

    Game fetchGame(GameIdentifier gameIdentifier);

    MoveResult moveTile(GameIdentifier gameIdentifier, String tileLabel);

}
