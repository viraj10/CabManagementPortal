package com.cabmanagement.entity.operator;

import com.cabmanagement.entity.value.AccountStatus;

public class System extends Account{
    public System(String id, String password, AccountStatus status, Person person) {
        super(id, password, status, person);
    }
}
