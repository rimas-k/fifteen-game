package com.someorg.fifteengame.factories;

import com.someorg.fifteengame.model.Game;

public interface GameFactory {
    Game createRandomGame(int boardSize);
}
