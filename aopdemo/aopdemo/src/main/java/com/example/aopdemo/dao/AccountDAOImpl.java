package com.example.aopdemo.dao;

import com.example.aopdemo.Account;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository

public class AccountDAOImpl  implements AccountDAO{
    @Override
    public void addAccount(int account,boolean vip) {
        System.out.println("Adding account");
    }

    @Override
    public List<Account> findAccount() {
        List<Account>accounts=new ArrayList<>();
        accounts.add(new Account("blabla","blabla"));
        accounts.add(new Account("shshshs","hshshs"));
        accounts.add(new Account("samer","Gold"));
        return accounts;
    }
}
