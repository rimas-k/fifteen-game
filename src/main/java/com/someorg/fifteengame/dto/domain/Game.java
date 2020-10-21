package com.someorg.fifteengame.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class Game {
    private int boardSize;

    private List<Tile> tiles;
}
