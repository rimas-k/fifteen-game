package com.someorg.fifteengame.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MoveTileRequest {
    @NotBlank
    private String userId;

    @NotBlank
    private String gameId;

    @NotBlank
    private String tileId;
}
