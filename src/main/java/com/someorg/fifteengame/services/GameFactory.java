package com.someorg.fifteengame.services;

import com.someorg.fifteengame.model.domain.Game;

public interface GameFactory {
     Game createRandomGame(int boardSize);
}
