package com.someorg.fifteengame.services;

import com.someorg.fifteengame.dto.FetchGameRequest;
import com.someorg.fifteengame.dto.domain.Game;
import com.someorg.fifteengame.repositories.exceptions.GameNotFoundException;

public interface FetchGameService {
    Game fetchGame(FetchGameRequest request) throws GameNotFoundException;
}
