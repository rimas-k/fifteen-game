package com.someorg.fifteengame.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class CreateGameRequest {
    @NotBlank
    private String userId;

    @NotBlank
    private String gameId;

    @Min(2)
    private int boardSize;
}
