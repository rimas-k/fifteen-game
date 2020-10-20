package com.someorg.fifteengame.services;

import com.someorg.fifteengame.dto.MoveTileRequest;
import com.someorg.fifteengame.dto.MoveTileResponse;

public interface MoveTileService {
    MoveTileResponse moveTile(MoveTileRequest request);
}
