package com.cabmanagement.entity;

import java.util.Objects;

public class City {
    private final String name;
    private boolean active;

    public City(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return name.equals(city.name) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", active=" + active +
                '}';
    }
}
