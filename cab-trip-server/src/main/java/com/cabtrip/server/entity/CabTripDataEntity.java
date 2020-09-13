package com.cabtrip.server.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * The CabTripData jpa entity.
 *
 * Note this table has no natural key field(s) so using @EqualsAndHashCode
 * I would normally have a base class that implements an equals and hashCode by an id compare.
 * https://deinum.biz/2019-02-13-Lombok-Data-Ojects-Arent-Entities/
 */
@Setter
@Getter
@EqualsAndHashCode
@Entity
@Table (name = "cab_trip_data")
public class CabTripDataEntity {

    @Id
    private String medallion;

    private String hackLicense;

    private String vendorId;

    private Long rateCode;

    private String storeAndFwdFlag;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDate pickupDatetime;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDate dropoffDatetime;

    private Long passengerCount;

    private Long tripTimeInSecs;

    private Double tripDistance;

    private Double pickupLongitude;

    private Double pickupLatitude;

    private Double dropoffLongitude;

    private Double dropoffLatitude;

}
