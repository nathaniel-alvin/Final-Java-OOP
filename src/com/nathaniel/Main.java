package com.nathaniel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final String profileFileName = "profiles.txt";
        final String accountFileName = "accounts.txt";
        final String bankName = "Not Your Bank";
        final int quitNum = 0;

        HashMap<Integer, Profile> profileHashMap = new HashMap<>();
        HashMap<Long, Account> accountHashMap = new HashMap<>();

        scanInput input = new scanInput(new Scanner(System.in));
        ProfileListMan profileListManager = new ProfileListMan(profileHashMap);
        ProfileFileMan profileFileManager = new ProfileFileMan(profileListManager, profileFileName);
        AccountListMan accountListManager = new AccountListMan(accountHashMap);
        AccountFileMan accountFileManager = new AccountFileMan(accountListManager, accountFileName);

        Account account = null;
        boolean printMenu = true;

        System.out.println("Welcome to " + bankName + " !\n");
        System.out.print("Enter your name: ");
        String name = input.getString().trim();
        System.out.print("Enter your 4-digit ID number: ");
        int ID = input.getInt();
        // check if profile exist
        Profile profile = profileListManager.find(ID, name);

        String accountNumberStr = "";

        String userInput;
        appLoop:
        do {
            int pinAttempt = 0;

            // if no profile found
            if (profile == null) {
                System.out.println("\nOptions: ");
                System.out.println("0. Quit");
                System.out.println("1. Create a profile");
                System.out.print("Input: ");
                userInput = input.getString();

                switch (Integer.parseInt(userInput)) {
                    case quitNum: //Quit
                        System.out.println("\nGoodbye!");
                        break appLoop;
                    case 1: // Create new Bank Profile
                        profile = new Profile(name, ID, new LinkedList<>());
                        profileListManager.add(ID, profile);
                        profileFileManager.add(profile);

                        System.out.println("\nProfile created! Your name and ID were used.");
                        continue appLoop;
                    default: //Invalid Option
                        System.out.println("Invalid Option. Try again.");
                        break;
                }
            }

            do {
                if (printMenu) {
                    System.out.println("\nProfile Actions Menu");
                    System.out.println("---------");
                    System.out.println("0. Quit");
                    System.out.println("1. Remove profile");
                    System.out.println("2. Add an account");
                    System.out.println("3. Manage an account");
                    printMenu = false;
                }

                System.out.println("\nPlease enter your action");
                System.out.print("Input: ");
                userInput = input.getString();

                switch (Integer.parseInt(userInput)) {
                    case quitNum: //Quit
                        System.out.println("\nGoodbye!");
                        break appLoop;
                    case 1: //Remove profile
                        accountFileManager.removeAccounts(profile);
                        accountListManager.removeAccounts(profile);

                        profileFileManager.remove(profile);
                        profileListManager.remove(ID);

                        System.out.println("Profile Removed. Goodbye!");
                        break appLoop;
                    case 2: //Create account
                        System.out.print("\nInitial Deposit: $");
                        double deposit = input.getDouble();
                        if (deposit <= 0 || deposit > 10000) continue;

                        System.out.print("Re-enter your ID number: ");
                        ID = input.getInt();
                        if (!profile.verifyID(ID)) continue;

                        System.out.print("Please enter a 4-digit Pin: ");
                        Pin pin = new Pin(input.getInt());

                        AccountNumber accountNumber = new AccountNumber(ID);
                        account = new Account(accountNumber, pin, deposit);

                        accountListManager.add(accountNumber.getAccountNum(), account);
                        accountFileManager.add(account);
                        profileFileManager.remove(profile);
                        profile.addAcc(accountNumber.getAccountNum());
                        profileFileManager.add(profile);

                        System.out.println("Account created!");
                        break;
                    case 3: //Manage account
                        if (profile.hasAccount()) {
                            System.out.println("\nWhich account do you want to work on?");
                            profile.showAccounts();

                            System.out.print("Account Number: ");
                            accountNumberStr = input.getString().trim();

                            account = accountListManager.find(Long.parseLong(accountNumberStr));
                            if (account == null) continue;

                            System.out.println("\nYou will have 3 attempts to enter your pin.");

                            pinAttemptLoop:
                            do {
                                System.out.print("Pin: ");

                                if (account.verifyPin(input.getInt())) break pinAttemptLoop;
                                else pinAttempt++;

                                if (pinAttempt == 3) {
                                    System.out.println("\nYou ran out of attempts. Please try again later. Goodbye!");
                                    break appLoop;
                                }
                            } while (pinAttempt <= 2);
                        } else {
                            System.out.println("This profile has no accounts.");
                            continue;
                        }
                        break;
                    default: //Invalid Option
                        System.out.println("Invalid Option. Try again.");
                        break;
                }
            } while (account == null);

            printMenu = true;

            actionLoop:
            do {
                if (printMenu) {
                    System.out.println("\nAccount Actions Menu");
                    System.out.println("---------");
                    System.out.println("0. Quit");
                    System.out.println("1. Deposit Money");
                    System.out.println("2. Withdraw Money");
                    System.out.println("3. Transfer Money");
                    System.out.println("4. New Pin");
                    System.out.println("5. Delete this account");
                    System.out.println("6. Account information");
                    printMenu = false;
                }

                System.out.println("\nWhat would you like to do?");
                System.out.print("Input: ");
                userInput = input.getString();

                switch (Integer.parseInt(userInput)) {
                    case quitNum: //Quit
                        System.out.println("\nGoodbye!");
                        break appLoop;
                    case 1: //Deposit Money
                        System.out.print("\nDeposit Amount: $");
                        accountFileManager.remove(account);
                        account.deposit(input.getDouble());
                        accountFileManager.add(account);
                        break;
                    case 2: //Withdraw Money
                        System.out.print("\nWithdrawal Amount: $");
                        accountFileManager.remove(account);
                        account.withdraw(input.getDouble());
                        accountFileManager.add(account);
                        break;
                    case 3: //Transfer Money
                        System.out.print("\nAccount Number of the Target Account: ");
                        profile.showAccounts();
                        Account tempTargetAccount = accountListManager.find(input.getLong());
                        if (tempTargetAccount == null) break;
                        System.out.print("Transfer Amount: $");
                        accountFileManager.remove(account);
                        accountFileManager.remove(tempTargetAccount);
                        account.transfer(tempTargetAccount, input.getDouble());
                        accountFileManager.add(account);
                        accountFileManager.add(tempTargetAccount);
                        break;
                    case 4://Enter a New Pin
                        System.out.print("\nNew Pin: ");
                        int updatedPin = input.getInt();

                        if (account.is4Digit(updatedPin)) {
                            accountFileManager.remove(account);
                            account.updatePin(updatedPin);
                            accountFileManager.add(account);
                        }
                        break;
                    case 5: //Delete this account
                        accountFileManager.remove(account);
                        accountListManager.remove(Long.parseLong(accountNumberStr));
                        profile.remAcc(Long.parseLong(accountNumberStr));
                        account = null;

                        System.out.println("\nAccount deleted!\nPlease restart the application.");
                        break;
                    case 6: //Show Account Information
                        account.showInfo();
                        break;
                    default: //Invalid Option
                        System.out.println("Invalid Option. Try again.");
                        break;
                }
            } while (Integer.parseInt(userInput) != quitNum);
        } while (Integer.parseInt(userInput) != quitNum);
        input.close();
    }
}