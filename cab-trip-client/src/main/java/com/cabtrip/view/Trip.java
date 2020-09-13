package com.cabtrip.view;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Trip {
    private String medallion;

    private String pickupDate;

    private int tripCount;

    public Trip(String medallion, String pickupDate) {
        this.medallion = medallion;
        this.pickupDate = pickupDate;
    }
}
