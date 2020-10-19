package com.someorg.fifteengame.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Tile {

    private Position position;

    private String tileLabel;

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class Position {
        private int x;

        private int y;
    }

}
