package com.nathaniel;

public class Account implements Transaction {
    private AccountNumber accountNumber;
    private Pin pin;
    private double balance;

    // Account constructor initializes attributes
    public Account(AccountNumber accountNumber, Pin pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public boolean verifyPin(int pinAttempt) {
        return pin.verifyPin(pinAttempt);
    }

    public boolean is4Digit (int pinAttempt) {
        return pin.is4Digit(pinAttempt);
    }

    public void updatePin(int newPin) {
        pin.updatePin(newPin);
    }

    private void getBalance() {
        System.out.println("Current Balance: " + balance);
    }

    private boolean isValidWithdraw(double amount) {
        if (balance - amount < 0) {
            System.out.println("Failure: Insufficient funds");
            return false;
        } else return true;
    }
    public void deposit(double amount) {
        balance += amount;
        getBalance();
    }

    public void withdraw(double amount) {
        if (!isValidWithdraw(amount)) return;

        balance -= amount;
        getBalance();
    }

    public void transfer(Account target, double amount) {
        if (!isValidWithdraw(amount)) return;

        withdraw(amount);
        target.deposit(amount);
        getBalance();
    }

    public void showInfo() {
        System.out.println();
        getBalance();
        accountNumber.showInfo();
        pin.showInfo();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(balance).append(",").append(pin.toString()).append(",").append(accountNumber.toString());
        return sb.toString();
    }
}
