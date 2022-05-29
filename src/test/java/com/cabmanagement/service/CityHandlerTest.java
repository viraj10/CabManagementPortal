package com.cabmanagement.service;

import com.cabmanagement.entity.City;
import com.cabmanagement.store.CityRepository;
import com.cabmanagement.store.DataBaseStore;
import com.cabmanagement.store.InMemoryRepository;
import com.cabmanagement.util.CabBookingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CityHandlerTest {

    private CityHandler cityHandler;

    @BeforeEach
    void setUp(){
        CityRepository cityRepository = new InMemoryRepository(new DataBaseStore());
        cityHandler = new CityHandler(cityRepository, new CabFinder());
    }

    @Test
    void onBoardCity() {
        assertEquals(0, cityHandler.getCities().size());
        cityHandler.onBoardCity("Pune");
        assertEquals(1, cityHandler.getCities().size());
    }

    @Test
    void activateCity() {
        assertEquals(0, cityHandler.getCities().size());
        City pune = cityHandler.onBoardCity("Pune");
        cityHandler.inactivateCity(pune);
        assertEquals(0, cityHandler.getCities().size());
        cityHandler.activateCity(pune);
        assertEquals(1, cityHandler.getCities().size());
    }

    @Test
    void inactivateCity() {
        assertEquals(0, cityHandler.getCities().size());
        City pune = cityHandler.onBoardCity("Pune");
        cityHandler.inactivateCity(pune);
        assertEquals(0, cityHandler.getCities().size());
    }

    @Test
    void getCities() {
        assertEquals(0, cityHandler.getCities().size());
        cityHandler.onBoardCity("Pune");
        cityHandler.onBoardCity("Mumbai");
        assertEquals(2, cityHandler.getCities().size());
    }
}