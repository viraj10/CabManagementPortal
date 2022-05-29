package com.cabmanagement.entity.operator;

import com.cabmanagement.entity.value.AccountStatus;

public abstract class Account {
    private String id;
    private String password;
    private AccountStatus status;
    private Person person;

    public Account(String id, String password, AccountStatus status, Person person) {
        this.id = id;
        this.password = password;
        this.status = status;
        this.person = person;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public Person getPerson() {
        return person;
    }
}
