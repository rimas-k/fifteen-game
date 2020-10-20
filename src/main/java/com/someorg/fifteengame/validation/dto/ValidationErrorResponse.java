package com.someorg.fifteengame.validation.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {

    @Getter
    private List<Violation> violations = new ArrayList<>();

    public void addToViolations(Violation violation) {
        violations.add(violation);
    }

}
