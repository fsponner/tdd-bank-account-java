package org.xpdojo.bank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest{

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Test
    public void newAccountWithoutBalance(){
        Account account = new Account();
        assertThat(account.balance()).isEqualTo(0);
    }

    @Test
    public void newAccountWithTenBalance(){
        Account account = new Account(10);
        assertThat(account.balance()).isEqualTo(10);
    }

    @Test
    public void depositTenToEmptyAccount(){
        Account account = new Account();
        account.deposit(10);
        assertThat(account.balance()).isEqualTo(10);
    }

    @Test
    public void depositTenAndTwentyToEmptyAccount(){
        Account account = new Account();
        account.deposit(10);
        account.deposit(20);
        assertThat(account.balance()).isEqualTo(30);
    }

    @Test
    public void withdrawTenAndTwentyFromAccount() {
        Account account = new Account(30);
        account.withdraw(10);
        account.withdraw(20);
        assertThat(account.balance()).isEqualTo(0);
    }

    @Test
    public void depositAndWithdrawTenToInitialAccount() {
        Account account = new Account();
        account.deposit(10);
        account.withdraw(10);
        assertThat(account.balance()).isEqualTo(0);
    }

    @Test
    public void withdrawNegativeAccountBalanceException(){
        Account account = new Account();
        Assertions.assertThrows(IllegalArgumentException.class, () -> account.withdraw(10));
    }

    @Test
    public void transferTenBetweenAccounts(){
        Account from = new Account(10);
        Account to = new Account(0);
        to = from.transfer(to, 10);
        assertThat(from.balance()).isEqualTo(0);
        assertThat(to.balance()).isEqualTo(10);
    }

    @Test
    public void getAccountBalanceSlip(){
        Account account = new Account();
        Date date = new Date();
        assertThat(account.balanceSlip()).isEqualTo(dateFormat.format(date) + " " + account.balance());
    }

    @Test
    public void addStatementEntryToStatement(){
        Account account = new Account();
        account.addStatementEntry("DEPOST",10);
    }

    @Test
    public void getEmptyAccountStatement(){
        Account account = new Account();
        assertThat(account.statement().isEmpty()).isEqualTo(true);
    }

    @Test
    public void getAccountStatementWithOneEntry(){
        Account account = new Account();
        Date date = new Date();
        account.addStatementEntry("Deposit", 10);
        assertThat(account.statement().size()).isEqualTo(1);
        assertThat(account.statement().get(0)[0]).isEqualTo("Deposit");
        assertThat(account.statement().get(0)[1]).isEqualTo(10);
        assertThat(account.statement().get(0)[2]).isEqualTo(dateFormat.format(date));
        assertThat(account.balanceSlip()).isEqualTo(dateFormat.format(date) + " " + account.balance());

    }

    @Test
    public void getStatementAfterDeposit(){
        Account account = new Account();
        account.deposit(10);
        assertThat(account.statement().size()).isEqualTo(1);
        assertThat(account.statement().get(0)[0]).isEqualTo("Deposit");
        assertThat(account.statement().get(0)[1]).isEqualTo(10);
    }

    @Test
    public void getFilteredStatement(){
        Account account = new Account();
        Account transferAccount = new Account();
        account.deposit(10);
        account.deposit(20);
        account.withdraw(5);
        account.transfer(transferAccount, 5);
        assertThat(account.filteredStatement("Withdrawal").size()).isEqualTo(2);
        for (Object[] entry : account.filteredStatement("Withdrawal")) {
            assertThat(entry[0]).isEqualTo("Withdrawal");
        }
    }
}
