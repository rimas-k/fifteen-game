package com.someorg.fifteengame.services.util;

import com.someorg.fifteengame.domain.Game;
import com.someorg.fifteengame.domain.Tile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DTOConverter {

    public com.someorg.fifteengame.dto.domain.Game createGameDTO(Game game) {
        com.someorg.fifteengame.dto.domain.Game gameDTO = new com.someorg.fifteengame.dto.domain.Game();

        gameDTO.setBoardSize(game.getBoardSize());

        List<Tile> tiles = game.getTiles();

        List<com.someorg.fifteengame.dto.domain.Tile> tileDTOs = new ArrayList<>();

        for (Tile tile : tiles) {
            com.someorg.fifteengame.dto.domain.Tile tileDTO = createTileDTO(tile);
            tileDTOs.add(tileDTO);
        }

        gameDTO.setTiles(tileDTOs);
        gameDTO.setGameComplete(game.isGameInCompleteState());

        return gameDTO;
    }

    public com.someorg.fifteengame.dto.domain.Tile createTileDTO(Tile tile) {
        com.someorg.fifteengame.dto.domain.Tile tileDTO = new com.someorg.fifteengame.dto.domain.Tile();

        tileDTO.setLabel(tile.getTileLabel());
        tileDTO.setPositionX(tile.getPosition().getX());
        tileDTO.setPositionY(tile.getPosition().getY());

        return tileDTO;
    }

}
