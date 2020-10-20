package com.someorg.fifteengame.validation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Violation {
    private String element;

    private String message;
}
