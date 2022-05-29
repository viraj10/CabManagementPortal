package com.cabmanagement.store;

import com.cabmanagement.entity.Cab;
import com.cabmanagement.entity.City;
import com.cabmanagement.entity.Reservation;

import java.util.List;
import java.util.Map;

public interface ReservationRepository {

    void saveReservation(Reservation reservation);
    void updateReservation(Reservation reservation);
    Reservation getReservation(String reservationId);

    List<Reservation> allReservationsForCity(City city);
    Map<City, List<Reservation>> allReservationsByCity();

    List<Reservation> allReservationsForCab(String cabRcNumber);
}
