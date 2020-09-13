package com.cabtrip.client;

public interface TripClient {

    void tripCount(String csvFilename, boolean useCache);

    void clearCache();

}
