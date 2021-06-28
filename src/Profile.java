// class for profile

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

public class Profile {
    private String name;
    private int ID; // 4-digit custom ID
    private LinkedList<Long> accountNumberList;

    public Profile(String name, int ID, LinkedList<Long> accountNumberList) {
        this.name = name;
        this.ID = ID;
        this.accountNumberList = accountNumberList;
    }

    // check if name exist
    public boolean verifyName(String nameAttempt) { return nameAttempt.equals(name); }

    // check valid ID
    public boolean verifyID(int ID) { return this.ID == ID; }

    // Check if there is account for the profile
    public boolean hasAccount() {
        if (accountNumberList.isEmpty()) return false;
        else return true;
    }

    // add account
    public void addAcc(long accountNumber) {
        accountNumberList.add(accountNumber);
    }

    // remove account
    public void remAcc(long accountNumber) {
        accountNumberList.remove(accountNumber);
    }

    // check if inputted accountNumber is in the profile
    public boolean checkAccount(long accNumber) {
        for (Iterator<Long> i = accountNumberList.iterator(); i.hasNext(); ) {
            Long accountNumber = i.next();
            if (accountNumber == accNumber) return true;
        }
        return false;
    }

    public void showAccounts() {
        Long accountNumber;
        int count = 1;

        System.out.println("\nAccounts: ");
        for (Iterator<Long> i = accountNumberList.iterator(); i.hasNext(); ) {
            accountNumber = i.next();
            System.out.println(count + ": " + accountNumber);
            count++;
        }
    }
    public void showInfo() {
        Long accountNumber;

        System.out.println("Name: " + name);
        System.out.println("Account Numbers: ");
        for (Iterator<Long> i = accountNumberList.iterator(); i.hasNext(); ) {
            accountNumber = i.next();
            System.out.println(accountNumber + ",");
        }
        System.out.println();
    }

    @Override
    public String toString() {
        Long accountNumber;
        StringBuilder sb = new StringBuilder();

        sb.append(name).append(",").append(ID);
        for (Iterator<Long> i = accountNumberList.iterator(); i.hasNext(); ) {
            accountNumber = i.next();
            sb.append(",").append(accountNumber);
        }
        return sb.toString();
    }

    //
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return ID == profile.ID && name.equals(profile.name) && accountNumberList.equals(profile.accountNumberList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ID, accountNumberList);
    }
}
