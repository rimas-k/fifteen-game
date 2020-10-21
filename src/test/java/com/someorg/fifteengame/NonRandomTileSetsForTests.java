package com.someorg.fifteengame;

import com.someorg.fifteengame.domain.Tile;

import java.util.Arrays;
import java.util.List;

public class NonRandomTileSetsForTests {

    public static final List<Tile> BOARD_SIZE_2_ONE_STEP_TO_COMPLETION = Arrays.asList(
            new Tile(new Tile.Position(0,0), "1"),
            new Tile(new Tile.Position(0,1), "2"),
            new Tile(new Tile.Position(1,0), "_"),
            new Tile(new Tile.Position(1,1), "3"));

}
