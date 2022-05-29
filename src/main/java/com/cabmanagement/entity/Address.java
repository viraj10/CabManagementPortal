package com.cabmanagement.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Address {
    private City city;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public Address(City city) {
        this.city = city;
    }

    public Address(City city, BigDecimal latitude, BigDecimal longitude) {
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public City getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city) && Objects.equals(latitude, address.latitude) && Objects.equals(longitude, address.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, latitude, longitude);
    }
}
