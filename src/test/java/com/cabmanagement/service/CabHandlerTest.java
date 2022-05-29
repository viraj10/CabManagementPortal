package com.cabmanagement.service;

import com.cabmanagement.entity.Address;
import com.cabmanagement.entity.City;
import com.cabmanagement.entity.operator.Driver;
import com.cabmanagement.entity.operator.Person;
import com.cabmanagement.entity.value.AccountStatus;
import com.cabmanagement.entity.value.CabStatus;
import com.cabmanagement.entity.value.CabType;
import com.cabmanagement.store.CabRepository;
import com.cabmanagement.store.CityRepository;
import com.cabmanagement.store.DataBaseStore;
import com.cabmanagement.store.InMemoryRepository;
import com.cabmanagement.util.CabBookingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CabHandlerTest {

    private CabHandler cabHandler;
    private CabRepository cabRepository;
    private CityRepository cityRepository;
    private CabFinder cabFinder;

    @BeforeEach
    void setUp(){
        DataBaseStore dataBaseStore = new DataBaseStore();
        cabRepository = new InMemoryRepository(dataBaseStore);
        cabFinder = new CabFinder();
        cityRepository = new InMemoryRepository(dataBaseStore);
        cabHandler = new CabHandler(cabRepository, cityRepository, cabFinder);
    }

    @Test
    void addCab_wrongCity() {
        Person viraj = new Person("viraj", "v@g.c", "808763");
        Driver driver = new Driver("dri1", "password", AccountStatus.ACTIVE, viraj,"vira13");
        City thane = new City("Thane", true);
        assertThrows(IllegalArgumentException.class,
                () -> cabHandler.addCab("rc1", CabType.ECONOMY,"Hyundai","creta", 2021L, driver, thane));
    }

    @Test
    void addCab_alreadyPresent() {
        Person viraj = new Person("viraj", "v@g.c", "808763");
        Driver driver = new Driver("dri1", "password", AccountStatus.ACTIVE, viraj,"vira13");
        City thane = addCity();
        String rcNumber = "rc2";
        cabHandler.addCab(rcNumber, CabType.ECONOMY,"Hyundai","creta", 2021L, driver, thane);
        assertThrows(IllegalArgumentException.class,
                () -> cabHandler.addCab(rcNumber, CabType.ECONOMY,"Hyundai","creta", 2021L, driver, thane));

    }

    @Test
    void addCab_success() {
        Person viraj = new Person("viraj", "v@g.c", "808763");
        Driver driver = new Driver("dri1", "password", AccountStatus.ACTIVE, viraj,"vira13");
        City thane = addCity();
        String rcNumber = "rc5";
        cabHandler.addCab(rcNumber, CabType.ECONOMY,"Hyundai","creta", 2021L, driver, thane);
        assertEquals(rcNumber, cabRepository.getCab(rcNumber).getRcNumber());
    }

    @Test
    void updateCabLocation() {
        Person viraj = new Person("viraj", "v@g.c", "808763");
        Driver driver = new Driver("dri1", "password", AccountStatus.ACTIVE, viraj,"vira13");
        City thane = addCity();
        String rcNumber = "rc4";
        cabHandler.addCab(rcNumber, CabType.ECONOMY,"Hyundai","creta", 2021L, driver, thane);
        assertEquals(rcNumber, cabRepository.getCab(rcNumber).getRcNumber());
        Address pune = new Address(new City("Pune", true));
        cabHandler.updateCabLocation(rcNumber, pune);
        assertEquals(pune, cabRepository.getCab(rcNumber).getCabLocation());
    }

    @Test
    void updateCabStatus() {
        Person viraj = new Person("viraj", "v@g.c", "808763");
        Driver driver = new Driver("dri1", "password", AccountStatus.ACTIVE, viraj,"vira13");
        City thane = addCity();
        String rcNumber = "rc3";

        cabHandler.addCab(rcNumber, CabType.ECONOMY,"Hyundai","creta", 2021L, driver, thane);
        assertEquals(rcNumber, cabRepository.getCab(rcNumber).getRcNumber());
        cabHandler.updateCabStatus(rcNumber, CabStatus.LOST);
        assertEquals(CabStatus.LOST, cabRepository.getCab(rcNumber).getCabStatus());
    }

    private City addCity() {
        City thane = new City("Thane", true);
        cityRepository.addCity(thane);
        cabFinder.onboardCity(thane, CabBookingStrategy.oldestCabFirst());
        return thane;
    }
}