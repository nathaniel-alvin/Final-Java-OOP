package com.nathaniel;

import java.util.Scanner;

// create a scanInput class to remove the next line problem
public class scanInput {
    public Scanner sc;

    public scanInput(Scanner sc) {
        this.sc = sc;
    }

    public int getInt() {
        return Integer.parseInt(sc.nextLine());
    }

    public long getLong() {
        return Long.parseLong((sc.nextLine()));
    }

    public double getDouble() {
        return Double.parseDouble((sc.nextLine()));
    }

    public String getString() {
        return sc.nextLine();
    }

    // close scanner that has been opened, cannot open another scanner after closing
    public void close() {
        sc.close();
    }
}

