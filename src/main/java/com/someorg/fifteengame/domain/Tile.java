package com.someorg.fifteengame.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
