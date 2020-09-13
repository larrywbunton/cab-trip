package com.cabtrip.view;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class Trip {
    @NonNull
    private String medallion;

    @NonNull
    private LocalDate pickupDate;

    private int tripCount;
}
