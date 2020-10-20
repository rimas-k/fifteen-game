package com.someorg.fifteengame.services;

import com.someorg.fifteengame.dto.CreateGameRequest;
import com.someorg.fifteengame.dto.domain.Game;
import com.someorg.fifteengame.repositories.exceptions.GameAlreadyExistsException;

public interface CreateGameService {
    Game createGame(CreateGameRequest request) throws GameAlreadyExistsException;
}
