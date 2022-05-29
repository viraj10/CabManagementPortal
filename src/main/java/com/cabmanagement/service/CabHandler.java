package com.cabmanagement.service;

import com.cabmanagement.entity.Address;
import com.cabmanagement.entity.Cab;
import com.cabmanagement.entity.City;
import com.cabmanagement.entity.operator.Driver;
import com.cabmanagement.entity.value.CabType;
import com.cabmanagement.entity.value.CabStatus;
import com.cabmanagement.store.CabRepository;
import com.cabmanagement.store.CityRepository;

import java.time.LocalDateTime;

public class CabHandler {
    private final CabRepository cabRepository;
    private final CityRepository cityRepository;
    private final CabFinder cabFinder;

    public CabHandler(CabRepository cabRepository, CityRepository cityRepository, CabFinder cabFinder) {
        this.cabRepository = cabRepository;
        this.cityRepository = cityRepository;
        this.cabFinder = cabFinder;
    }

    public Cab addCab(String rcNumber, CabType cabType, String manufacturingCompany, String modelName, Long yearOfRegistration, Driver driver, City city) {
        if (!cityRepository.cityPresent(city.getName())) {
            throw new IllegalArgumentException(String.format("City not served yet %s", city));
        }
        checkIfCabAvailable(rcNumber);
        Cab cab = new Cab(rcNumber, cabType, manufacturingCompany, modelName, yearOfRegistration, city, driver);
        cab.setVehicleStatus(CabStatus.AVAILABLE);
        cab.setAvailableFrom(LocalDateTime.now());
        cabRepository.addCab(cab);
        cabFinder.addToAvailableQueue(city, cab);
        return cab;
    }

    private Cab checkIfCabAvailable(String rcNumber) {
        Cab cab = cabRepository.getCab(rcNumber);
        if (cab != null) {
            throw new IllegalArgumentException(String.format("Cab with rcNumber %s already present", rcNumber));
        }
        return cab;
    }

    public void updateCabLocation(String rcNumber, Address cabLocation) {
        Cab cab = cabRepository.getCab(rcNumber);
        if (cab == null) {
            throw new IllegalArgumentException(String.format("Cab with rcNumber %s not present", rcNumber));
        }
        cab.setCabLocation(cabLocation);
        cabRepository.save(cab);
    }

    void updateCabStatus(String rcNumber, CabStatus cabStatus) {
        Cab cab = cabRepository.getCab(rcNumber);
        if (cab == null) {
            throw new IllegalArgumentException(String.format("Cab with rcNumber %s not present", rcNumber));
        }
        cab.setVehicleStatus(cabStatus);
        cabRepository.save(cab);
    }

}
