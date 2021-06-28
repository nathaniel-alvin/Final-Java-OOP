package com.nathaniel;

import java.util.HashMap;

public class ProfileListMan {
    private HashMap<Integer, Profile> profileList;

    public ProfileListMan(HashMap<Integer, Profile> profileList) {
        this.profileList = profileList;
    }

    // add profile to list
    public void add(int key, Profile profile) {
        profileList.put(key, profile);
    }

    // remove profile from list
    public void remove(int key) {
        profileList.remove(key);
    }

    // find account in the list using ID
    public Profile find(int key, String name) {
        Profile tempProfile = profileList.get(key);

        if (tempProfile != null && tempProfile.verifyName(name)) return tempProfile;

        System.out.println("Bank Profile Not Found");
        return null;
    }
}
