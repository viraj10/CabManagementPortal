package com.cabmanagement.store;

import com.cabmanagement.entity.Address;
import com.cabmanagement.entity.Cab;
import com.cabmanagement.entity.Reservation;
import com.cabmanagement.entity.operator.Driver;
import com.cabmanagement.entity.value.CabStatus;

import java.util.Set;

public interface CabRepository {
    Set<Cab> getCabs();
    Set<Cab> getAvailableCabs();
    boolean cabPresent(String rcNumber);
    void addCab(Cab cab);
    Cab getCab(String rcNumber);

    void save(Cab cab);
    //void endReservation(Reservation reservation);
}
