package com.someorg.fifteengame.services;

import com.someorg.fifteengame.dto.CreateGameRequest;
import com.someorg.fifteengame.dto.CreateGameResponse;

public interface CreateGameService {
    CreateGameResponse createGame(CreateGameRequest request);
}
