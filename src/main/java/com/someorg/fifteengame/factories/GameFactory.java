package com.someorg.fifteengame.factories;

import com.someorg.fifteengame.model.domain.Game;

public interface GameFactory {
    Game createRandomGame(int boardSize);
}
