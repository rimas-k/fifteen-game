package com.someorg.fifteengame.dto;

import com.someorg.fifteengame.common.RegexpConstants;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class MoveTileRequest {
    @NotBlank
    @Pattern(regexp = RegexpConstants.ALPHANUMERIC)
    private String userId;

    @NotBlank
    @Pattern(regexp = RegexpConstants.ALPHANUMERIC)
    private String gameId;

    @NotBlank
    @Pattern(regexp = RegexpConstants.NUMERIC)
    private String tileLabel;
}
