package com.cabmanagement.service;

import com.cabmanagement.entity.Cab;
import com.cabmanagement.entity.City;
import com.cabmanagement.store.CityRepository;
import com.cabmanagement.util.CabBookingStrategy;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class CityHandler {

    private final CityRepository cityRepository;
    private final CabFinder cabFinder;

    public CityHandler(CityRepository cityRepository, CabFinder cabFinder) {
        this.cityRepository = cityRepository;
        this.cabFinder = cabFinder;
    }

    public City onBoardCity(String cityName) {
        return onBoardCity(cityName, CabBookingStrategy.oldestCabFirst());
    }

    public City onBoardCity(String cityName, Comparator<Cab> cabComparator) {
        City city = new City(cityName, true);
        cityRepository.addCity(city);
        cabFinder.onboardCity(city, cabComparator);
        return city;
    }

    public void activateCity(City city) {
        cityRepository.activateCity(city);
    }

    public void inactivateCity(City city) {
        cityRepository.inactivateCity(city);
    }

    public Set<City> getCities() {
        return new HashSet<>(cityRepository.getCities());
    }

}

