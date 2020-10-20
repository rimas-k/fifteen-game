package com.someorg.fifteengame.services;

import com.someorg.fifteengame.dto.FetchGameRequest;
import com.someorg.fifteengame.dto.domain.Game;

public interface FetchGameService {
    Game fetchGame(FetchGameRequest request);
}
