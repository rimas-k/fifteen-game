package com.someorg.fifteengame.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameIdentifier {
    private String userId;

    private String gameId;
}
