package com.someorg.fifteengame.model.domain;


import com.someorg.fifteengame.common.MoveResult;
import com.someorg.fifteengame.factories.impl.GameFactoryImpl;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Game {

    @Getter
    private int boardSize;

    @Getter
    private List<Tile> tiles;

    @Getter(AccessLevel.PROTECTED)
    private Map<String, Tile> tileLabelToTileMap;

    @Getter(AccessLevel.PROTECTED)
    private Map<Tile.Position, Tile> tilePositionToTileMap;

    public Game(int boardSize, List<Tile> tiles) {
        this.boardSize = boardSize;
        this.tiles = tiles;

        fillTileLabelToTileMap();
        fillTilePositionToTileMap();
    }

    public MoveResult moveTileIntoBlankPosition(String tileLabel) {
        synchronized (this) {

            Tile blankTile = tileLabelToTileMap.get(GameFactoryImpl.BLANK_TILE_LABEL);
            Tile.Position blankPosition = blankTile.getPosition();

            Tile requestedTile = tileLabelToTileMap.get(tileLabel);
            Tile.Position requestedTilePosition = requestedTile.getPosition();

            boolean areNeighbours = areTilesNeighbours(blankPosition, requestedTilePosition);
            MoveResult result;

            if (!areNeighbours) {
                result = MoveResult.ILLEGAL_MOVE;
            } else {
                exchangeTilePositions(blankTile, requestedTile);
                boolean isGameComplete = checkIfGameIsComplete();

                if (isGameComplete) {
                    result = MoveResult.GAME_COMPLETE;
                } else {
                    result = MoveResult.OK;
                }
            }

            return result;
        }
    }

    private boolean checkIfGameIsComplete() {
        boolean isGameComplete = true;

        for (Tile tile : tiles) {
            Tile.Position position = tile.getPosition();
            String tileLabel = tile.getTileLabel();

            if (GameFactoryImpl.BLANK_TILE_LABEL.equals(tileLabel)) {
                Tile.Position targetBlankPosition = getTargetBlankPosition();
                isGameComplete = isGameComplete && targetBlankPosition.equals(position);
            } else {
                String targetTileLabel = createTargetTileLabelByPosition(position);
                isGameComplete = isGameComplete && tileLabel.equals(targetTileLabel);
            }
        }

        return isGameComplete;
    }

    private String createTargetTileLabelByPosition(Tile.Position position) {
        Integer targetTileLabelNumber = boardSize * position.getY() + position.getX();
        return targetTileLabelNumber.toString();
    }

    private Tile.Position getTargetBlankPosition() {
        Tile.Position position = new Tile.Position(boardSize - 1, boardSize - 1);
        return position;
    }

    private void exchangeTilePositions(Tile blankTile, Tile requestedTile) {
        Tile.Position oldReqPosition = requestedTile.getPosition();
        Tile.Position oldBlankPosition = blankTile.getPosition();

        changeTilePosition(blankTile, oldReqPosition);
        changeTilePosition(requestedTile, oldBlankPosition);
    }

    private void changeTilePosition(Tile tile, Tile.Position newPosition) {
        tile.setPosition(newPosition);
        tilePositionToTileMap.put(newPosition, tile);
    }

    private boolean areTilesNeighbours(Tile.Position firstPosition, Tile.Position secondPosition) {
        double distanceSquareX = Math.pow(firstPosition.getX() - secondPosition.getX(), 2);
        double distanceSquareY = Math.pow(firstPosition.getY() - secondPosition.getY(), 2);

        double distance = Math.sqrt(distanceSquareX + distanceSquareY);
        double epsilon = 1E-5;

        // if the distance is exactly 1, then the tiles are switchable neighbours
        boolean isNeighbour = distance - 1 < epsilon;

        return isNeighbour;
    }

    private void fillTileLabelToTileMap() {
        tileLabelToTileMap = new ConcurrentHashMap<>();

        for (Tile tile : tiles) {
            tileLabelToTileMap.put(tile.getTileLabel(), tile);
        }
    }

    private void fillTilePositionToTileMap() {
        tilePositionToTileMap = new ConcurrentHashMap<>();

        for (Tile tile : tiles) {
            tilePositionToTileMap.put(tile.getPosition(), tile);
        }
    }

}
