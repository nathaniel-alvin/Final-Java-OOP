package com.nathaniel;

import java.io.*;
import java.util.LinkedList;

public class ProfileFileMan extends FileMan {
    private ProfileListMan profileListManager;

    public ProfileFileMan(ProfileListMan profileListManager, String fileName) {
        super(fileName);
        this.profileListManager = profileListManager;

        setup();
    }

    //load profiles into the system from a local file
    private void setup() {
        try {
            String[] tempParse;
            File profileFile = new File(fileName);
            if (!profileFile.exists()) profileFile.createNewFile();
            BufferedReader buffR = new BufferedReader(new FileReader(profileFile));
            //BufferedReader buffR = new BufferedReader(new FileReader(getFileName()));

            do {
                tempParse = parseLine(buffR);
                if (tempParse == null) break;
                addProfileFromFile(tempParse);
            } while (true);

            buffR.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //add a profile from a line parse
    private void addProfileFromFile(String[] splitLine) {
        Profile profile;
        LinkedList<Long> accountNumberList = new LinkedList<>();

        for (int i = 2; i <= splitLine.length - 1; i++) {
            accountNumberList.add(Long.parseLong(splitLine[i].trim()));
        }

        profile = new Profile(splitLine[0].trim(), Integer.parseInt(splitLine[1].trim()), accountNumberList);
        profileListManager.add(Integer.parseInt(splitLine[1].trim()), profile);
    }

    //add a profile to the file
    public void add(Profile profile) {
        try {
            //open the buffer in append mode
            BufferedWriter buffW = new BufferedWriter(new FileWriter(fileName, true));

            buffW.write((profile.toString()).trim());
            buffW.newLine();

            buffW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //remove a profile from the file
    public void remove(Profile profile) {
        removeLineFromFile(profile.toString());
    }
}

