package com.cabmanagement.store;

import com.cabmanagement.entity.Cab;
import com.cabmanagement.entity.City;
import com.cabmanagement.entity.Reservation;

import java.util.*;
import java.util.stream.Collectors;

public class DataBaseStore {

    private Set<City> cities = new HashSet<>();
    private Map<String, Cab> cabMap = new HashMap();

    private Map<String, Reservation> reservationMap = new HashMap();

    public Set<City> getCities() {
        return Collections.unmodifiableSet(cities);
    }

    public boolean cityPresent(String cityName) {
        return cities.parallelStream()
                .anyMatch(city -> cityName.equals(city.getName()));
    }

    public void addCity(City city) {
        cities.add(city);
    }

    public void inactivateCity(City city) {
        cities.remove(city);
        city.setActive(false);
        cities.add(city);
    }

    public void activateCity(City city) {
        cities.remove(city);
        city.setActive(true);
        cities.add(city);
    }

    public Set<Cab> getCabs() {
        return cabMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toSet());
    }

    public boolean cabPresent(String rcNumber) {
        return cabMap.containsKey(rcNumber);
    }

    public void addCab(Cab cab) {
        cabMap.put(cab.getRcNumber(), cab);
    }

    public Cab getCab(String rcNumber) {
        return cabMap.get(rcNumber);
    }

    public void putReservation(String reservationId, Reservation reservation) {
        reservationMap.put(reservationId, reservation);
    }

    public Reservation getReservation(String reservationId) {
        return reservationMap.get(reservationId);
    }

    public Collection<Reservation> allReservations() {
        return reservationMap.values();
    }
}
