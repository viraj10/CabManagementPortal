package com.cabmanagement.store;

import com.cabmanagement.entity.Cab;
import com.cabmanagement.entity.City;
import com.cabmanagement.entity.Reservation;
import com.cabmanagement.entity.value.CabStatus;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class InMemoryRepository implements CityRepository, CabRepository, ReservationRepository {

    private final DataBaseStore dataBaseStore;

    public InMemoryRepository(DataBaseStore dataBaseStore) {
        this.dataBaseStore = dataBaseStore;
    }

    @Override
    public Set<City> getCities() {
        return dataBaseStore.getCities().stream().filter(City::isActive).collect(Collectors.toSet());
    }

    @Override
    public boolean cityPresent(String cityName) {
        return dataBaseStore.cityPresent(cityName);
    }

    @Override
    public void addCity(City city) {
        dataBaseStore.addCity(city);
    }

    @Override
    public void inactivateCity(City city) {
        dataBaseStore.inactivateCity(city);
    }

    @Override
    public void activateCity(City city) {
        dataBaseStore.activateCity(city);
    }

    @Override
    public Set<Cab> getCabs() {
        return dataBaseStore.getCabs();
    }

    @Override
    public Set<Cab> getAvailableCabs() {
        return dataBaseStore.getCabs().stream()
                .filter(cab -> CabStatus.AVAILABLE.equals(cab.getVehicleStatus()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean cabPresent(String rcNumber) {
        return dataBaseStore.cabPresent(rcNumber);
    }

    @Override
    public void addCab(Cab cab) {
        dataBaseStore.addCab(cab);
    }

    @Override
    public Cab getCab(String rcNumber) {
        return dataBaseStore.getCab(rcNumber);
    }

    @Override
    public void save(Cab cab) {
        dataBaseStore.addCab(cab);
    }

    @Override
    public void saveReservation(Reservation reservation) {
        reservation.getCab().addReservations(reservation);
        reservation.getCustomer().addReservation(reservation);
        dataBaseStore.putReservation(reservation.getReservationId(), reservation);
    }

    @Override
    public void updateReservation(Reservation reservation) {
        dataBaseStore.putReservation(reservation.getReservationId(), reservation);
    }

    @Override
    public Reservation getReservation(String reservationId) {
        return dataBaseStore.getReservation(reservationId);
    }

    @Override
    public List<Reservation> allReservationsForCity(City city) {
        return dataBaseStore.allReservations().stream()
                .filter(reservation -> city.equals(reservation.getStartFrom().getCity()))
                .collect(Collectors.toList());
    }

    @Override
    public Map<City, List<Reservation>> allReservationsByCity() {
        return dataBaseStore.allReservations().stream()
                .collect(Collectors.groupingBy(r -> r.getStartFrom().getCity()));
    }

    @Override
    public List<Reservation> allReservationsForCab(String cabRcNumber) {
        return dataBaseStore.allReservations().stream()
                .filter(reservation -> cabRcNumber.equals(reservation.getCab().getRcNumber()))
                .collect(Collectors.toList());
    }
}
