package com.someorg.fifteengame.dto.domain;

import lombok.Data;

import java.util.List;

@Data
public class Game {
    private int boardSize;

    private List<Tile> tiles;
}