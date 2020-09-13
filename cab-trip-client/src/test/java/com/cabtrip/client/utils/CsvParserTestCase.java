package com.cabtrip.client.utils;

import com.cabtrip.view.Trip;
import com.cabtrip.view.TripList;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CsvParserTestCase {

    /**
     * medallion,pickup date
     * D7D598CD99978BD012A87A76A7C891B7,2013-12-01
     * 664A1D03258065503DE78FC496906AA5,2013-12-31
     */

    @Test
    public void importTripsFromCsv_success() throws URISyntaxException {
        URL resource = CsvParserTestCase.class.getResource("in.csv");
        File file = Paths.get(resource.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();

        TripList tripList = CsvParser.getTripListFromCsv(absolutePath);

        assertNotNull(tripList);
        assertNotNull(tripList.getTrips());
        List<Trip> trips = tripList.getTrips();
        assertEquals(2, tripList.getTrips().size());
        Trip trip1 = trips.get(0);
        assertEquals("first record medallion equals", "D7D598CD99978BD012A87A76A7C891B7", trip1.getMedallion());

        Trip trip2 = trips.get(1);
        assertEquals("pickup date matches 2nd record", "2013-12-31", trip2.getPickupDate());
    }

    @Test
    public void importTripsFromCsv_FailNoFile() {
        ApplicationException applicationException = Assert.assertThrows(ApplicationException.class, () -> CsvParser.getTripListFromCsv("blahblahblah.notafile"));
        assertEquals("Invalid Input", applicationException.getName());
        assertEquals("Error occurred while reading the input CSV file.", applicationException.getContext());
        assertEquals("java.io.FileNotFoundException: blahblahblah.notafile (The system cannot find the file specified)", applicationException.getCause().toString());
    }

    @Test
    public void importTripsFromCsv_FailBadCSV() throws URISyntaxException {
        URL resource = CsvParserTestCase.class.getResource("bad.csv");
        File file = Paths.get(resource.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();

        ApplicationException applicationException = Assert.assertThrows(ApplicationException.class, () -> CsvParser.getTripListFromCsv(absolutePath));
        assertEquals("Invalid Input", applicationException.getName());
        assertEquals("Input CSV should have lines that contains 2 tokens: <medallion>,<pickUpDate>", applicationException.getContext());
        assertNull(applicationException.getCause());
    }

}
