package com.cabmanagement.entity.operator;

import com.cabmanagement.entity.value.AccountStatus;

public class Driver extends Account{
    private String licenseId;

    public Driver(String id, String password, AccountStatus status, Person person, String licenseId) {
        super(id, password, status, person);
        this.licenseId = licenseId;
    }
}
