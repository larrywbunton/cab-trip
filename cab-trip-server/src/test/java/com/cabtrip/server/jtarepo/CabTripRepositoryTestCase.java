package com.cabtrip.server.jtarepo;

import com.cabtrip.server.entity.CabTripDataEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * These are the test cases for the CabTripDao.
 */

@Transactional
@SpringBootTest
public class CabTripRepositoryTestCase {

    private static final String MEDALLION = "test";
    private static final LocalDate NOW = LocalDate.now();

    // Tests to run against populated mysql cabdb database
    private static final String MEDALLION_POPULATED = "D7D598CD99978BD012A87A76A7C891B7";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final LocalDate LOCAL_DATE = LocalDate.parse("2013-12-01", DATE_FORMATTER);

    @Autowired
    private CabTripRepository dao;

    @Test
    public void successMatch() {
        // create a record to query against
        CabTripDataEntity data1 = new CabTripDataEntity();
        data1.setMedallion(MEDALLION);
        data1.setPickupDatetime(NOW);
        data1 = dao.save(data1);

        int tripCount = dao.getTripCount(MEDALLION, NOW, NOW.plusDays(1));
        assertEquals(1, tripCount, "tripCount == 1");
    }

    @Test
    public void failMatch() {
        // create a record to query against
        CabTripDataEntity data1 = new CabTripDataEntity();
        data1.setMedallion(MEDALLION);
        data1.setPickupDatetime(NOW);
        data1 = dao.save(data1);

        int tripCount = dao.getTripCount(MEDALLION, NOW.plusDays(10), NOW.plusDays(11));
        assertEquals(0, tripCount, "tripCount == 0");
    }

    @Test
    public void behaviorOfNullParams() {
        //Check null params behavior
        int tripCount = dao.getTripCount(null, null, null);
        assertEquals(0, tripCount, "null params tripCount == 0");
    }

    @Test
    public void successPopulatedDatabase() {
        Integer tripCount = dao.getTripCount(MEDALLION_POPULATED, LOCAL_DATE, LOCAL_DATE.plusDays(1));
        assertEquals(3, tripCount, "tripCount == 3");

        //Medallion does not match returns 0
        Integer tripCount2 = dao.getTripCount("BLAH NOT A MEDALLION", LOCAL_DATE, LOCAL_DATE.plusDays(1));
        assertEquals(0, tripCount2, "tripCount == 0");

        //Empty String no match
        Integer tripCount3 = dao.getTripCount("", LOCAL_DATE, LOCAL_DATE.plusDays(1));
        assertEquals(0, tripCount3, "tripCount == 0");

        //Blank String no match
        Integer tripCount4 = dao.getTripCount("    ", LOCAL_DATE, LOCAL_DATE.plusDays(1));
        assertEquals(0, tripCount4, "tripCount == 0");
    }
}
