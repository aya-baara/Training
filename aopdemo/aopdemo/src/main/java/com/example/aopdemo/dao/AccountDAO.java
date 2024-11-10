package com.example.aopdemo.dao;

import com.example.aopdemo.Account;

import java.util.List;

public interface AccountDAO {
    void addAccount(int account,boolean vip);
    List<Account> findAccount();
}
