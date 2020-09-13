package com.cabtrip.server.controller;

import com.cabtrip.server.jtarepo.CabTripRepository;
import com.cabtrip.view.GenericResponse;
import com.cabtrip.view.Trip;
import com.cabtrip.view.TripList;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
public class ServerController {
    private final Logger logger = LoggerFactory.getLogger(ServerController.class);

    @Autowired
    private CabTripRepository cabTripRepository;

    LoadingCache<Trip, Integer> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build(CacheLoader.from(this::getTripCountFromDatabase));

    /**
     * Clears the cache.
     * @return a generic json response
     */
    @GetMapping(path = "/clear-cache")
    public GenericResponse clearCache() {
        logger.info("clearCache()");
        cache.invalidateAll();
        return new GenericResponse(GenericResponse.RESULT_SUCCESS);
    }

    /**
     *  Returns a trip list that has a count for each trip.
     *
     * @param requestTripList the request trip list containing the trip with medallion and pickupDate
     * @param useCache if true will used the cache when looking up the trip count.
     * @return the trip list including the trip count
     */
    @PostMapping(path = "/cab-trip-count")
    public TripList cabTripCount(@RequestBody TripList requestTripList, @RequestParam boolean useCache) {
        if (requestTripList == null || CollectionUtils.isEmpty(requestTripList.getTrips())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid requestTripList parameter");
        }

        TripList responseTripList = new TripList();

        List<Trip> tripList = requestTripList.getTrips()
                .stream()
                .map(ti -> getTripWithCount(ti, useCache))
                .collect(Collectors.toList());

        responseTripList.setTrips(tripList);

        return responseTripList;
    }

    Trip getTripWithCount(Trip tripFromRequest, boolean useCache) {
        final Trip resultTrip = new Trip(tripFromRequest.getMedallion(), tripFromRequest.getPickupDate());
        if (StringUtils.isEmpty(tripFromRequest.getMedallion())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "trip.medallion is missing in the request");
        }

        int count = 0;
        if (useCache) {
            try {
                count = cache.get(tripFromRequest);

            } catch (ExecutionException e) {
                logger.error("getTripInfoWithCount(), exception retrieving from cache tripInfo=" + tripFromRequest.toString(), e);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            count = getTripCountFromDatabase(tripFromRequest);
            cache.put(tripFromRequest, count);
        }

        resultTrip.setTripCount(count);

        return resultTrip;
    }

    private int getTripCountFromDatabase(Trip info) {
        LocalDate pickupDate = info.getPickupDate();
        logger.info("GetCountFromDatabase(). Cab Trip Request for medallion={}, pickupDate={}", info.getMedallion(), pickupDate);

        int count = cabTripRepository.getTripCount(info.getMedallion(), pickupDate, pickupDate.plusDays(1));

        logger.info("GetCountFromDatabase() Success. Total of {} Cab Trips for medallion: {}, pickupDate = {}", count, info.getMedallion(), pickupDate);

        return count;
    }
}