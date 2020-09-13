package com.cabtrip.server.jtarepo;

import com.cabtrip.server.entity.CabTripDataEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface CabTripRepository extends CrudRepository<CabTripDataEntity, String> {

    @Query("select count(c) from CabTripDataEntity c where c.medallion = ?1 and c.pickupDatetime >= ?2 and pickupDatetime < ?3")
    int getTripCount(String medallion, LocalDate pickupStartDate, LocalDate pickupEndDate);

}
