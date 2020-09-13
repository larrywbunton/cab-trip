package com.cabtrip.client;

import com.cabtrip.client.invoker.ApiInvoker;
import com.cabtrip.client.utils.ApplicationException;
import com.cabtrip.client.utils.CsvParser;
import com.cabtrip.view.GenericResponse;
import com.cabtrip.view.TripList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Trip Client implementation provided methods to call trip count and clear cache and output results to the console.
 */
public class TripClientImpl implements TripClient {

    private static final Logger logger = LoggerFactory.getLogger(TripClientImpl.class);

    private final ApiInvoker apiInvoker;

    public TripClientImpl(String baseUrl) {
        apiInvoker = new ApiInvoker(baseUrl);
    }

    /**
     * Imports the csvFile the invokes the server rest trip-count api.
     * If it successfully connects to the server it will output results to the console.
     * If an error occurs it will log the exception and output details to the console.
     *
     * @param csvFilename the csv file containing the cab trip details medallion and pickupDate
     * @param useCache specify for the Cab Trip server to use cache.
     */
    public void tripCount(String csvFilename, boolean useCache) {
        try {
            TripList tripListRequest = CsvParser.getTripListFromCsv(csvFilename);
            TripList tripListResponse = apiInvoker.cabTripCount(tripListRequest, useCache);

            tripListResponse.getTrips().forEach(System.out::println);

        } catch (ApplicationException ae) {
            logger.error(ae.getName(), ae);
            System.out.println(ae.toString());
            if (ae.getCause() != null) {
                System.out.println(ae.getCause().toString());
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            System.out.println("An error occurred getting the trip count from the CabTrip server.");
            System.out.println(e.toString());
            System.exit(1);
        }
    }

    /**
     * Calls the Cab Trip Server rest endpoint clear-cache.
     * Output the successful result or details the error to the console.
     */
    public void clearCache() {
        try {
            GenericResponse genericResponse = apiInvoker.clearCache();
            System.out.println("Clear Cache Result: " + genericResponse.getResult());

        } catch (ApplicationException ae) {
            logger.error(ae.getName(), ae);
            System.out.println(ae.toString());
            if (ae.getCause() != null) {
                System.out.println(ae.getCause().toString());
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            System.out.println("An error occurred clearing the CabTrip server's cache.");
            System.out.println(e.toString());
            System.exit(1);
        }
    }
}
