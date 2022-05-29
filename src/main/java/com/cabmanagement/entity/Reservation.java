package com.cabmanagement.entity;

import com.cabmanagement.entity.operator.Customer;
import com.cabmanagement.entity.operator.Driver;
import com.cabmanagement.entity.value.PaymentStatus;
import com.cabmanagement.entity.value.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Reservation implements Comparable<Reservation> {

    private final String reservationId;
    private final LocalDateTime bookedAt;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private Address startFrom;
    private Address endTo;
    private final Cab cab;
    private ReservationStatus status;
    private PaymentStatus paymentStatus;
    private final Customer customer;
    private BigDecimal charges;

    public Reservation(String reservationId, LocalDateTime bookedAt, Address startFrom,
                       Address endTo, Cab cab, ReservationStatus status,
                       PaymentStatus paymentStatus, Customer customer) {
        this.reservationId = reservationId;
        this.bookedAt = bookedAt;
        this.startFrom = startFrom;
        this.endTo = endTo;
        this.cab = cab;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.customer = customer;

    }

    public String getReservationId() {
        return reservationId;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public Address getStartFrom() {
        return startFrom;
    }

    public Address getEndTo() {
        return endTo;
    }

    public Cab getCab() {
        return cab;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public BigDecimal getCharges() {
        return charges;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setCharges(BigDecimal charges) {
        this.charges = charges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cab)) return false;
        Reservation reservation = (Reservation) o;
        return reservationId.equals(reservation.reservationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId);
    }

    @Override
    public int compareTo(Reservation o) {
        return this.getStartedAt().compareTo(o.getStartedAt());
    }
}
