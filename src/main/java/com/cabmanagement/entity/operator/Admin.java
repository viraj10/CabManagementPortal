package com.cabmanagement.entity.operator;

import com.cabmanagement.entity.value.AccountStatus;

import java.time.LocalDate;

public class Admin extends Account{
    private LocalDate dateJoined;

    public Admin(String id, String password, AccountStatus status, Person person, LocalDate dateJoined) {
        super(id, password, status, person);
        this.dateJoined = dateJoined;
    }
}
