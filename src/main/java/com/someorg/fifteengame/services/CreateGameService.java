package com.someorg.fifteengame.services;

import com.someorg.fifteengame.dto.CreateGameRequest;
import com.someorg.fifteengame.dto.domain.Game;

public interface CreateGameService {
    Game createGame(CreateGameRequest request);
}
