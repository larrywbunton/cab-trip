package com.cabtrip.view;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse {
    public static final String RESULT_SUCCESS = "SUCCESS";

    private String result;
}
