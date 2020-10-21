package com.someorg.fifteengame.factories;

import com.someorg.fifteengame.domain.Game;

public interface GameFactory {
    String BLANK_TILE_LABEL = "_";

    Game createRandomGame(int boardSize);
}
