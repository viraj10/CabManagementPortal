package com.cabmanagement.store;

import com.cabmanagement.entity.City;

import java.util.Set;

public interface CityRepository {
    Set<City> getCities();
    boolean cityPresent(String cityName);
    void addCity(City city);
    void inactivateCity(City city);
    void activateCity(City city);
}
