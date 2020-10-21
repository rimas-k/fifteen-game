package com.someorg.fifteengame.testutil;

import com.someorg.fifteengame.domain.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NonRandomTileSetsForTests {

    public static final List<Tile> BOARD_SIZE_2_ONE_STEP_TO_COMPLETION = Arrays.asList(
            new Tile(new Tile.Position(0,0), "1"),
            new Tile(new Tile.Position(1,0), "2"),
            new Tile(new Tile.Position(0,1), "_"),
            new Tile(new Tile.Position(1,1), "3"));

    public static List<Tile> getDeepCopyOf(List<Tile> sourceList) {
        List<Tile> deepCopy = new ArrayList<Tile>();

        for(Tile tile : sourceList) {
            deepCopy.add(new Tile(tile.getPosition(), tile.getTileLabel()));
        }

        return deepCopy;
    }
}
