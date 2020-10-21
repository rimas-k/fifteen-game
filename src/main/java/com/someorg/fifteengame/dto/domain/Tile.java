package com.someorg.fifteengame.dto.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Tile {
    private String label;

    private int positionX;

    private int positionY;
}
