package com.someorg.fifteengame.factories.impl;

import com.someorg.fifteengame.model.domain.Game;
import com.someorg.fifteengame.model.domain.Tile;
import com.someorg.fifteengame.factories.GameFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class GameFactoryImpl implements GameFactory {

    public static final String BLANK_TILE_LABEL = "_";

    public Game createRandomGame(int boardSize) {
        int tilesCount = boardSize * boardSize - 1;

        Queue<String> tileLabels = generateRandomTileLabels(tilesCount);
        List<Tile> tiles = generateTiles(boardSize, tileLabels);

        Game game = new Game(boardSize, tiles);

        return game;
    }

    private Queue<String> generateRandomTileLabels(int tilesCount) {
        LinkedList<String> tileLabels = IntStream
                .range(1, tilesCount)
                .mapToObj(Integer::toString)
                .collect(Collectors.toCollection(LinkedList::new));

        tileLabels.add(BLANK_TILE_LABEL);
        Collections.shuffle(tileLabels);

        return tileLabels;
    }

    private List<Tile> generateTiles(int boardSize, Queue<String> tileLabels) {
        List<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Tile tile = new Tile();

                Tile.Position position = new Tile.Position(i, j);
                tile.setPosition(position);

                String tileLabel = tileLabels.remove();
                tile.setTileLabel(tileLabel);

                tiles.add(tile);
            }
        }

        return tiles;
    }

}
