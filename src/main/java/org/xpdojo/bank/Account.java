package org.xpdojo.bank;

import java.text.SimpleDateFormat;
import java.util.*;

public class Account {

    private int balance;
    private final List<Object[]> statement;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public Account(int amount) {
        this.balance += amount;
        this.statement = new ArrayList<>();
    }

    public Account() {
        this.balance = 0;
        this.statement = new ArrayList<>();
    }

    public int balance() {
        return this.balance;
    }

    public void deposit(int amount) {
        this.balance += amount;
        addStatementEntry("Deposit", amount);
    }

    public void withdraw(int amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficiant account balance!");
        }
        this.balance -= amount;
        addStatementEntry("Withdrawal", amount);
    }

    public Account transfer(Account to, int amount) {
        //Objects.requireNonNull(to, "account must not be null");
        this.withdraw(amount);
        to.deposit(amount);
        return to;
    }

    public String balanceSlip() {
        Date date = new Date();
        return dateFormat.format(date) + " " + balance;
    }

    public void addStatementEntry(String type, int amount) {
        Date date = new Date();
        Object[] entry = new Object[3];
        entry[0] = type;
        entry[1] = amount;
        entry[2] = dateFormat.format(date);
        statement.add(entry);
    }

    public List<Object[]> statement(){
        return statement;
    }

    public ArrayList<Object[]> filteredStatement(String type) {
        ArrayList<Object[]> filteredStatement;
        filteredStatement = new ArrayList<>();
        for(Object[] entry:statement){
            if(entry[0].equals(type)){
                filteredStatement.add(entry);
            }
        }
        return filteredStatement;
    }
}
