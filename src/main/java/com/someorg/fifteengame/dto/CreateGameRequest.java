package com.someorg.fifteengame.dto;

import com.someorg.fifteengame.common.RegexpConstants;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class CreateGameRequest {
    @NotBlank
    @Pattern(regexp = RegexpConstants.ALPHANUMERIC)
    private String userId;

    @NotBlank
    @Pattern(regexp = RegexpConstants.ALPHANUMERIC)
    private String gameId;

    @Min(2)
    @Max(9)
    private int boardSize;
}
