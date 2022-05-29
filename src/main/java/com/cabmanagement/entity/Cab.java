package com.cabmanagement.entity;

import com.cabmanagement.entity.operator.Driver;
import com.cabmanagement.entity.value.CabType;
import com.cabmanagement.entity.value.CabStatus;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Cab {

    private final String rcNumber;
    private final CabType cabType;
    private CabStatus cabStatus;
    private final String manufacturingCompany;
    private final String modelName;
    private final Long yearOfRegistration;
    private final City city;
    private Address cabLocation;
    private Driver driver;
    private Set<Reservation> reservations = new HashSet<>();
    private LocalDateTime availableFrom;

    public Cab(String rcNumber, CabType cabType,
               String manufacturingCompany, String modelName,
               Long yearOfRegistration, City city, Driver driver) {
        this.rcNumber = rcNumber;
        this.cabType = cabType;
        this.manufacturingCompany = manufacturingCompany;
        this.modelName = modelName;
        this.yearOfRegistration = yearOfRegistration;
        this.city = city;
        this.driver = driver;
    }

    public void setVehicleStatus(CabStatus cabStatus) {
        this.cabStatus = cabStatus;
    }

    public void setCabLocation(Address cabLocation) {
        this.cabLocation = cabLocation;
    }

    public void addReservations(Reservation reservations) {
        setVehicleStatus(CabStatus.RESERVED);
        this.reservations.add(reservations);
    }
    public void setAvailableFrom(LocalDateTime availableFrom) {
        this.availableFrom = availableFrom;
    }

    public String getRcNumber() {
        return rcNumber;
    }

    public CabType getCabType() {
        return cabType;
    }

    public CabStatus getVehicleStatus() {
        return cabStatus;
    }

    public String getManufacturingCompany() {
        return manufacturingCompany;
    }

    public String getModelName() {
        return modelName;
    }

    public Long getYearOfRegistration() {
        return yearOfRegistration;
    }

    public Address getCabLocation() {
        return cabLocation;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public LocalDateTime getAvailableFrom() {
        return availableFrom;
    }

    public CabStatus getCabStatus() {
        return cabStatus;
    }

    public City getCity() {
        return city;
    }

    public Driver getDriver() {
        return driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cab)) return false;
        Cab cab = (Cab) o;
        return rcNumber.equals(cab.rcNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rcNumber);
    }
}
