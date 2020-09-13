package com.cabtrip.client.invoker;

import com.cabtrip.client.utils.ApplicationException;
import com.cabtrip.view.GenericResponse;
import com.cabtrip.view.TripList;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Consts;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Utility class for invoking the rest endpoints on the cab trip server.
 */
public class ApiInvoker {
    private static final String SERVER_CLEAR_CACHE_API = "/clear-cache";
    private static final String SERVER_GET_TRIP_INFO_API = "/cab-trip-count";

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    private final ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(ApiInvoker.class);

    private final String baseUrl;

    /**
     * Constructor.
     * @param baseUrl the base url of the cab trip server.
     */
    public ApiInvoker(String baseUrl) {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.baseUrl = baseUrl;
    }

    /**
     * Makes a Get rest call to the clear-cache API.
     *
     * @return the generic response
     * @throws IOException if there is a problem successfully connecting to the server.
     */
    public GenericResponse clearCache() throws IOException {

        HttpGet request = new HttpGet(baseUrl + SERVER_CLEAR_CACHE_API);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            if (response.getStatusLine().getStatusCode() == 200) {
                String json = EntityUtils.toString(response.getEntity());
                return objectMapper.readValue(json, GenericResponse.class);
            } else {
                logger.info("Response code: {}", response.getStatusLine().getStatusCode());
                throw new ApplicationException("Invalid Response", "The CabTrip server returned a response status code:" + response.getStatusLine().getStatusCode());
            }
        }
    }

    /**
     * Makes a Post rest call to the trip-count API.
     *
     * @param requestTripList the trip list to be sent to the api. This includes the medallion and pickupDate
     * @param useCache the flag if true says to the server to use cache.
     * @return the TripList result that include the trip count.
     * @throws IOException if there is a problem successfully connecting to the server.
     */
    public TripList cabTripCount(TripList requestTripList, boolean useCache) throws IOException {
        String body = objectMapper.writeValueAsString(requestTripList);

        HttpPost postRequest = new HttpPost(baseUrl + SERVER_GET_TRIP_INFO_API + "?useCache=" + useCache);
        postRequest.setEntity(new StringEntity(body, ContentType.create("application/json", Consts.UTF_8)));

        try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
            if (response.getStatusLine().getStatusCode() == 200) {
                String json = EntityUtils.toString(response.getEntity());
                return objectMapper.readValue(json, TripList.class);
            } else {
                logger.info("Response code: {}", response.getStatusLine().getStatusCode());
                throw new ApplicationException("Invalid Response", "The CabTrip server returned a response status code:" + response.getStatusLine().getStatusCode());
            }
        }
    }

}
