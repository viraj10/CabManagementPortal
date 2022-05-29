package com.cabmanagement.service;

import com.cabmanagement.entity.Cab;
import com.cabmanagement.entity.City;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import static com.cabmanagement.entity.value.CabStatus.AVAILABLE;

public class CabFinder {

    private final Map<City, PriorityQueue<Cab>> cabsByCity = new HashMap<>();

    public void onboardCity(City city, Comparator<Cab> cabComparator) {
        if(!cabsByCity.containsKey(city)) {
            cabsByCity.put(city, new PriorityQueue<>(cabComparator));
        }
    }

    public void addToAvailableQueue(City city, Cab cab) {
        if(!cabsByCity.containsKey(city) || !AVAILABLE.equals(cab.getCabStatus())) {
            throw new IllegalStateException(String.format("Cannot add unavailable cab %s", cab.getRcNumber()));
        }
        cabsByCity.get(city).add(cab);
    }

    public Cab getCab(City city) {
        if(!cabsByCity.containsKey(city) ||cabsByCity.get(city).isEmpty()) {
            throw new IllegalStateException("No Cab available");
        }
        return cabsByCity.get(city).poll();
    }

    public int cabSizeForCity(City city) {
        return cabsByCity.getOrDefault(city, new PriorityQueue<>()).size();
    }
}
