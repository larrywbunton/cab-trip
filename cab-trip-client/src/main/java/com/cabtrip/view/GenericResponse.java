package com.cabtrip.view;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class GenericResponse {
    public static final String RESULT_SUCCESS = "SUCCESS";

    private String result;

    private GenericResponse() {}
}
