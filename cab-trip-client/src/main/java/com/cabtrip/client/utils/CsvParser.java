package com.cabtrip.client.utils;

import com.cabtrip.view.Trip;
import com.cabtrip.view.TripList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for importing the input csv containing the trip data.
 */
public class CsvParser {

    public static TripList getTripListFromCsv(String filename)  {
        List<Trip> trips = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean headerLine=true;
            while ((line = br.readLine()) != null) {

                if (headerLine) {
                    headerLine=false;
                    continue;
                }

                if (!line.trim().isEmpty()) {
                    String[] tokens = line.split(",");
                    if (tokens.length == 2) {
                        LocalDate.parse(tokens[1].trim());
                        trips.add(new Trip(tokens[0].trim(), tokens[1].trim()));
                    } else {
                        throw new ApplicationException("Invalid Input", "Input CSV should have lines that contains 2 tokens: <medallion>,<pickUpDate>");
                    }

                }
            }

        } catch (ApplicationException ae) {
            throw ae;

        } catch (Exception e) {
            throw new ApplicationException("Invalid Input", e, "Error occurred while reading the input CSV file.");
        }

        return new TripList(trips);
    }
}
