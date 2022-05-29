package com.cabmanagement.service;

import com.cabmanagement.entity.Address;
import com.cabmanagement.entity.Cab;
import com.cabmanagement.entity.City;
import com.cabmanagement.entity.operator.Customer;
import com.cabmanagement.entity.operator.Driver;
import com.cabmanagement.entity.operator.Person;
import com.cabmanagement.entity.value.*;
import com.cabmanagement.store.*;
import com.cabmanagement.util.CabBookingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservationHandlerTest {
    public static final String RC_NUMBER = "rc7";
    ReservationHandler reservationHandler;
    private ReservationRepository reservationRepository;
    private CabFinder cabFinder;
    private CityRepository cityRepository;

    @BeforeEach
    void setUp(){
        DataBaseStore dataBaseStore = new DataBaseStore();
        cabFinder = new CabFinder();
        reservationRepository = new InMemoryRepository(dataBaseStore);
        cityRepository = new InMemoryRepository(dataBaseStore);
        reservationHandler = new ReservationHandler(cabFinder, reservationRepository, cityRepository);
    }

    @Test
    void addReservation_notValidCity() {
        City nagpur = new City("Nagpur", true);
        City mumbai = new City("Mumbai", true);
        addToAvailableQueue();
        assertThrows(IllegalArgumentException.class,
                () -> reservationHandler.addReservation("reseve1",new Address(nagpur), new Address(mumbai), PaymentStatus.COMPLETED, getCustomer()));
    }

    @Test
    void addReservation() {
        City pune = new City("Pune", true);
        City mumbai = new City("Mumbai", true);
        addToAvailableQueue();
        assertEquals(1, cabFinder.cabSizeForCity(pune));
        reservationHandler.addReservation("reseve1",new Address(pune), new Address(mumbai), PaymentStatus.COMPLETED, getCustomer());
        assertEquals(1, reservationHandler.getReservationsByCity(pune).size());
        assertEquals(1, reservationHandler.getReservationsByCab(RC_NUMBER).size());
        assertEquals(0, cabFinder.cabSizeForCity(pune));
    }

    @Test
    void endReservation() {
        City pune = new City("Pune", true);
        City mumbai = new City("Mumbai", true);
        addToAvailableQueue();
        String reservationId = "reseve1";
        reservationHandler.addReservation(reservationId,new Address(pune), new Address(mumbai), PaymentStatus.PENDING, getCustomer());
        assertEquals(ReservationStatus.ACTIVE, reservationHandler.getReservationsById(reservationId).getStatus());
        assertEquals(1, reservationHandler.getReservationsByCity(pune).size());
        reservationHandler.endReservation(reservationId, PaymentStatus.COMPLETED);
        assertEquals(ReservationStatus.COMPLETED, reservationHandler.getReservationsById(reservationId).getStatus());
        assertEquals(0, cabFinder.cabSizeForCity(pune));
        assertEquals(1, cabFinder.cabSizeForCity(mumbai));
    }

    private Customer getCustomer() {
        return new Customer("1", "password", AccountStatus.ACTIVE, new Person("viraj", "v@h.c", "8710"));
    }

    void addToAvailableQueue() {
        City pune = new City("Pune", true);
        City mumbai = new City("Mumbai", true);
        cityRepository.addCity(pune);
        cityRepository.addCity(mumbai);
        cabFinder.onboardCity(pune, CabBookingStrategy.oldestCabFirst());
        cabFinder.onboardCity(mumbai, CabBookingStrategy.oldestCabFirst());
        Cab cab = createCab(CabStatus.AVAILABLE, RC_NUMBER, pune);
        cabFinder.addToAvailableQueue(pune, cab);
    }

    private Cab createCab(CabStatus cabStatus, String rcNumber, City city) {
        Person viraj = new Person("sumit", "v@g.c", "808763");
        Driver driver = new Driver("dri1", "password", AccountStatus.ACTIVE, viraj,"vira13");
        Cab cab = new Cab(rcNumber, CabType.ECONOMY, "Hyundai", "creta", 2021L, city, driver);
        cab.setVehicleStatus(cabStatus);
        cab.setAvailableFrom(LocalDateTime.now());
        return cab;
    }
}