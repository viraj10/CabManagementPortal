package com.cabmanagement.service;

import com.cabmanagement.entity.Address;
import com.cabmanagement.entity.Cab;
import com.cabmanagement.entity.City;
import com.cabmanagement.entity.Reservation;
import com.cabmanagement.entity.operator.Customer;
import com.cabmanagement.entity.value.CabStatus;
import com.cabmanagement.entity.value.PaymentStatus;
import com.cabmanagement.entity.value.ReservationStatus;
import com.cabmanagement.store.CityRepository;
import com.cabmanagement.store.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ReservationHandler {
    private final CabFinder cabFinder;
    private final ReservationRepository reservationRepository;
    private final CityRepository cityRepository;

    public ReservationHandler(CabFinder cabFinder, ReservationRepository reservationRepository, CityRepository cityRepository) {
        this.cabFinder = cabFinder;
        this.reservationRepository = reservationRepository;
        this.cityRepository = cityRepository;
    }

    public void addReservation(String reservationId, Address startFrom,
                        Address endTo, PaymentStatus paymentStatus,
                        Customer customer) {

        City startFromCity = startFrom.getCity();
        if(!cityRepository.cityPresent(startFromCity.getName()) ||
                !cityRepository.cityPresent(endTo.getCity().getName()) || cabFinder.cabSizeForCity(startFromCity) == 0) {
            throw new IllegalArgumentException(String.format("Cab cannot be served for given route %s", startFromCity));
        }

        Cab cab = cabFinder.getCab(startFromCity);
        cab.setVehicleStatus(CabStatus.ON_TRIP);
        Reservation reservation = new Reservation(reservationId, LocalDateTime.now(), startFrom, endTo, cab, ReservationStatus.ACTIVE, paymentStatus, customer);
        reservation.setStatus(ReservationStatus.ACTIVE);
        reservationRepository.saveReservation(reservation);

    }

    public void endReservation(String reservationId, PaymentStatus paymentStatus) {
        Reservation reservation = reservationRepository.getReservation(reservationId);
        reservation.setEndedAt(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.COMPLETED);
        reservation.setPaymentStatus(paymentStatus);
        Cab cab = reservation.getCab();
        cab.setAvailableFrom(LocalDateTime.now());
        cab.setVehicleStatus(CabStatus.AVAILABLE);
        cabFinder.addToAvailableQueue(reservation.getEndTo().getCity(), cab);
    }

    public Reservation getReservationsById(String reservationId) {
        return reservationRepository.getReservation(reservationId);
    }

    public List<Reservation> getReservationsByCity(City city) {
        return reservationRepository.allReservationsForCity(city);
    }

    public Map<City, List<Reservation>> allReservations() {
        return reservationRepository.allReservationsByCity();
    }

    public List<Reservation> getReservationsByCab(String cabRcNumber) {
        return reservationRepository.allReservationsForCab(cabRcNumber);
    }
}
