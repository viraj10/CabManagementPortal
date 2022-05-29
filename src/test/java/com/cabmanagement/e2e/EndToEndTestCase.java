package com.cabmanagement.e2e;

import com.cabmanagement.entity.Address;
import com.cabmanagement.entity.Cab;
import com.cabmanagement.entity.City;
import com.cabmanagement.entity.Reservation;
import com.cabmanagement.entity.operator.Customer;
import com.cabmanagement.entity.operator.Driver;
import com.cabmanagement.entity.operator.Person;
import com.cabmanagement.entity.value.AccountStatus;
import com.cabmanagement.entity.value.PaymentStatus;
import com.cabmanagement.service.CabFinder;
import com.cabmanagement.service.CabHandler;
import com.cabmanagement.service.CityHandler;
import com.cabmanagement.service.ReservationHandler;
import com.cabmanagement.store.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.cabmanagement.entity.value.CabType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EndToEndTestCase {

   private ReservationHandler reservationHandler;
   private CabHandler cabHandler;
   private CityHandler cityHandler;
   private CabFinder cabFinder;

   @BeforeEach
   void setUp() {
       DataBaseStore dataBaseStore = new DataBaseStore();
       ReservationRepository reservationRepository = new InMemoryRepository(dataBaseStore);
       CityRepository cityRepository = new InMemoryRepository(dataBaseStore);
       CabRepository cabRepository = new InMemoryRepository(dataBaseStore);
       cabFinder = new CabFinder();
       cabHandler = new CabHandler(cabRepository, cityRepository, cabFinder);
       cityHandler = new CityHandler(cityRepository, cabFinder);
       reservationHandler = new ReservationHandler(cabFinder, reservationRepository, cityRepository);
   }

   @Test
   public void demo(){
       City pune = cityHandler.onBoardCity("Pune");
       City mumbai = cityHandler.onBoardCity("Mumbai");
       City bangalore = cityHandler.onBoardCity("Bangalore");

       cabHandler.addCab("pune1", ECONOMY, "Hyundai", "Scross", 2021L,
               driver("driver"), pune);
       cabHandler.addCab("pune2", COMPACT, "Honda", "City", 2021L,
               driver("driver"), pune);
       cabHandler.addCab("pune3", ECONOMY, "Suzuki", "Wagonr", 2021L,
               driver("driver"), pune);
       cabHandler.addCab("mumbai1", PREMIUM, "BMW", "530d", 2021L,
               driver("driver"), mumbai);
       cabHandler.addCab("mumbai2", COMPACT, "Jeep", "Mountain", 2021L,
               driver("driver"), mumbai);
       cabHandler.addCab("mumbai3", FULL_SIZE, "Mahindra", "Thar", 2021L,
               driver("driver"), mumbai);
       cabHandler.addCab("mumbai4", ECONOMY, "Toyoka", "Innova", 2021L,
               driver("driver"), mumbai);
       String bangalore1RcNumber = "bangalore1";
       Cab cab = cabHandler.addCab(bangalore1RcNumber, ECONOMY, "Hyundai", "Creata", 2021L,
               driver("driver"), bangalore);
       cab.setAvailableFrom(LocalDateTime.now().minusDays(1L));
       cabHandler.addCab("bangalore2", LUXURY, "Mercedes", "Formatica", 2021L,
               driver("driver"), bangalore);
       cabHandler.addCab("bangalore3", PREMIUM, "Suzuki", "Crezza", 2021L,
               driver("driver"), bangalore);
       cabHandler.addCab("bangalore4", FULL_SIZE, "Honda", "Ciaz", 2021L,
               driver("driver"), bangalore);

       String bangaloreToPune1 = "BangaloreToPune1";
       reservationHandler.addReservation(bangaloreToPune1,new Address(bangalore), new Address(pune), PaymentStatus.COMPLETED, customer("c1", "viraj"));
       String bangaloreToPune2 = "BangaloreToPune2";
       reservationHandler.addReservation(bangaloreToPune2,new Address(bangalore), new Address(pune), PaymentStatus.COMPLETED, customer("c2", "sid"));
       String bangaloreToMumbai3 = "BangaloreToMumbai3";
       reservationHandler.addReservation(bangaloreToMumbai3,new Address(bangalore), new Address(mumbai), PaymentStatus.COMPLETED, customer("c3", "ravi"));
       String puneToMumbai1 = "PuneToMumbai1";
       reservationHandler.addReservation(puneToMumbai1,new Address(pune), new Address(mumbai), PaymentStatus.COMPLETED, customer("c4", "suraj"));

       Optional<Map.Entry<City, List<Reservation>>> maximumBookingCity = reservationHandler.allReservations().entrySet().stream().max((a, b) -> a.getValue().size() - b.getValue().size());
       Map.Entry<City, List<Reservation>> cityListEntry = maximumBookingCity.get();
       System.out.printf("%s has most reservations ", cityListEntry.getKey().getName());

       List<Reservation> reservationsByCab = reservationHandler.getReservationsByCab(bangalore1RcNumber);
       System.out.printf("For %s there are %d reservation%n", bangalore1RcNumber, reservationsByCab.size());

        reservationHandler.endReservation(bangaloreToPune1, PaymentStatus.COMPLETED);
        reservationHandler.endReservation(bangaloreToPune2, PaymentStatus.COMPLETED);
        reservationHandler.endReservation(bangaloreToMumbai3, PaymentStatus.COMPLETED);
        reservationHandler.endReservation(puneToMumbai1, PaymentStatus.COMPLETED);

        assertEquals(4,  cabFinder.cabSizeForCity(pune)); // two came from bangalore and one pune car went to mumbai

   }

    private Driver driver(String name) {
        Person driver = new Person(name, "v@g.c", "808763");
        return new Driver("dri1", "password", AccountStatus.ACTIVE, driver,"vira13");
    }

    private Customer customer(String id, String name) {
        return new Customer(id, "password", AccountStatus.ACTIVE, new Person(name, "v@h.c", "8710"));
    }
}
