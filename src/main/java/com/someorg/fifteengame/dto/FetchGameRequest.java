package com.someorg.fifteengame.dto;

import com.someorg.fifteengame.common.RegexpConstants;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class FetchGameRequest {
    private String userId;

    private String gameId;
}
