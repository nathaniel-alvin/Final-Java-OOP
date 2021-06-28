package com.nathaniel;

import java.util.Random;

public class AccountNumber {

    private long accountNum;

    // Constructor with known acc number
    public AccountNumber(long accountNum) {this.accountNum = accountNum;}

    // Constructor for new acc
    // ID number is a 4 digit number
    public AccountNumber(int ID) { accountNum = generateAccNum(ID); }

    // Account number format:
    // 5-digit number
    // first 2 number = first 2 digit from IdNumber
    // last 3 number = 3-digit random number
    private long generateAccNum (int thisIdNumber ) {
        Random random = new Random();

        long tempAccNum = (long) ((thisIdNumber / 100) * Math.pow(10,3)); // first 2 digit from ID number
        tempAccNum += 100 + random.nextInt(900); // random 3 digit number
        return tempAccNum;
    }

    // verify account number from input
     public boolean verifyAccNum (Long accountNum) { return this.accountNum == accountNum; }

    public long getAccountNum () { return accountNum;}

    public void showInfo() {
        System.out.println("Account Number: " + accountNum);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(accountNum);
        return sb.toString();
    }
}
