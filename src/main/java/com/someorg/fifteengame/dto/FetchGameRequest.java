package com.someorg.fifteengame.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FetchGameRequest {
    @NotBlank
    private String userId;

    @NotBlank
    private String gameId;
}
