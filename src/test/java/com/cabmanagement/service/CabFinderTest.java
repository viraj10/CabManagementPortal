package com.cabmanagement.service;

import com.cabmanagement.entity.Cab;
import com.cabmanagement.entity.City;
import com.cabmanagement.entity.operator.Driver;
import com.cabmanagement.entity.operator.Person;
import com.cabmanagement.entity.value.AccountStatus;
import com.cabmanagement.entity.value.CabStatus;
import com.cabmanagement.entity.value.CabType;
import com.cabmanagement.util.CabBookingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class CabFinderTest {
    private CabFinder cabFinder;
    private Comparator<Cab> cabComparator = CabBookingStrategy.oldestCabFirst();

    @BeforeEach
    void setUp(){
        cabFinder = new CabFinder();
    }

    @Test
    void addToAvailableQueue_notAvailableError() {
        City thane = new City("Thane", true);
        Cab cab = createCab(CabStatus.LOST, "rc6", thane);
        cabFinder.onboardCity(thane, cabComparator);
        assertThrows(IllegalStateException.class, () -> cabFinder.addToAvailableQueue(thane, cab));
    }

    @Test
    void addToAvailableQueue_success() {
        City thane = new City("Thane", true);
        cabFinder.onboardCity(thane, cabComparator);
        Cab cab = createCab(CabStatus.AVAILABLE, "rc7", thane);
        cabFinder.addToAvailableQueue(thane, cab);
    }

    @Test
    void getCab() {
        City thane = new City("Thane", true);
        cabFinder.onboardCity(thane, cabComparator);
        Cab cabNew = createCab(CabStatus.AVAILABLE, "cabNew", new City("Thane", true));
        cabFinder.addToAvailableQueue(thane, cabNew);
        Cab cabOld = createCab(CabStatus.AVAILABLE, "cabOld", new City("Thane", true));
        cabOld.setAvailableFrom(LocalDateTime.now().minusDays(2));
        cabFinder.addToAvailableQueue(thane, cabOld);
        assertEquals("cabOld", cabFinder.getCab(thane).getRcNumber());
        assertEquals("cabNew", cabFinder.getCab(thane).getRcNumber());
    }

    @Test
    void getCab_multipleCity() {
        City thane = new City("Thane", true);
        City pune = new City("Pune", true);
        cabFinder.onboardCity(thane, cabComparator);
        cabFinder.onboardCity(pune, cabComparator);
        Cab cabNew = createCab(CabStatus.AVAILABLE, "cabNew", thane);
        cabFinder.addToAvailableQueue(thane, cabNew);
        Cab cabOld = createCab(CabStatus.AVAILABLE, "cabOld", pune);
        cabOld.setAvailableFrom(LocalDateTime.now().minusDays(2));
        cabFinder.addToAvailableQueue(pune, cabOld);
        assertEquals("cabOld", cabFinder.getCab(pune).getRcNumber());
        assertEquals("cabNew", cabFinder.getCab(thane).getRcNumber());
    }

    @Test
    void getCab_EmptyPool() {
        City thane = new City("Thane", true);
        cabFinder.onboardCity(thane, cabComparator);
        assertThrows(IllegalStateException.class, () -> cabFinder.getCab(thane));
    }
    private Cab createCab(CabStatus lost, String number, City city) {
        Person viraj = new Person("viraj", "v@g.c", "808763");
        Driver driver = new Driver("dri1", "password", AccountStatus.ACTIVE, viraj,"vira13");
        String rcNumber = number;
        Cab cab = new Cab(rcNumber, CabType.ECONOMY, "Hyundai", "creta", 2021L, city, driver);
        cab.setVehicleStatus(lost);
        cab.setAvailableFrom(LocalDateTime.now());
        return cab;
    }
}