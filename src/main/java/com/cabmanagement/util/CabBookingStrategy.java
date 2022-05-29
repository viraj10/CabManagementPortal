package com.cabmanagement.util;

import com.cabmanagement.entity.Cab;

import java.util.Comparator;

public class CabBookingStrategy  {

    public static Comparator<Cab> oldestCabFirst() {
        return Comparator.comparing(Cab::getAvailableFrom);
    }
}
