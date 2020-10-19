package com.someorg.fifteengame.dto;

import com.someorg.fifteengame.dto.domain.Game;
import lombok.Data;

@Data
public class FetchGameResponse {
    private Game game;
}
