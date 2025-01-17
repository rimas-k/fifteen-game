package com.someorg.fifteengame.repositories;

import com.someorg.fifteengame.common.MoveResult;
import com.someorg.fifteengame.domain.Game;
import com.someorg.fifteengame.domain.GameIdentifier;

public interface GameRepository {

    Game createGame(GameIdentifier gameIdentifier, int boardSize);

    Game fetchGame(GameIdentifier gameIdentifier);

    MoveResult moveTile(GameIdentifier gameIdentifier, String tileLabel);

}
