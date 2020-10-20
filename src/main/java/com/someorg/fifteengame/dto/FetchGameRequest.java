package com.someorg.fifteengame.dto;

import lombok.Data;

@Data
public class FetchGameRequest {
    private String userId;

    private String gameId;
}
