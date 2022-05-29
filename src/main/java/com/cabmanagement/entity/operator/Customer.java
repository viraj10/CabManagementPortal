package com.cabmanagement.entity.operator;

import com.cabmanagement.entity.Reservation;
import com.cabmanagement.entity.value.AccountStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Customer extends Account {

    private Set<Reservation> reservations = new HashSet<>();

    public Customer(String id, String password, AccountStatus status, Person person) {
        super(id, password, status, person);
    }

    public void addReservation(Reservation reservation){
        reservations.add(reservation);
    }
}
