package com.bildungsinstitut.util;

import java.util.HashSet;
import java.util.Random;

public class UniqueID {
    private static final HashSet<String> uniqueID = new HashSet<>();

    // generate random unique ID
    public static String generateUniqueID() {
        int lengthUniqueID = 10;
        String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
        Random random = new Random();
        String uniID;
        do {
            StringBuilder result = new StringBuilder();
            while (result.length() < lengthUniqueID) {
                int index = random.nextInt(characterSet.length());
                result.append(characterSet.charAt(index));
            }
            uniID = result.toString();
        } while (uniqueID.contains(uniID));
        uniqueID.add(uniID);
        return uniID;
    }
}
