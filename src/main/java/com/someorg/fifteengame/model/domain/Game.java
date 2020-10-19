package com.someorg.fifteengame.model.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Data
public class Game {
    @Getter
    @Setter
    private int boardSize;

    @Getter
    @Setter
    private List<Tile> tiles;

    @Getter
    @Setter
    private Map<Tile, Tile.Position> tilePositionMap;
}
