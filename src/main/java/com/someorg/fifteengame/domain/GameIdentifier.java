package com.someorg.fifteengame.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class GameIdentifier {
    private String userId;

    private String gameId;
}
