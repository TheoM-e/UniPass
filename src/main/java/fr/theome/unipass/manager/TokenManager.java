package fr.theome.unipass.manager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author TheoM
 * @github github.com/TheoM-e
 */

public class TokenManager {

    public String getRandomToken(){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(32);

        for (int i = 0; i < 32; i++) {
            int index = (int)(AlphaNumericString.length()* Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    public String getHashedToken(String token) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        assert md != null;
        md.update(token.getBytes());
        byte[] bytes = md.digest();

        StringBuilder stringBuilder = new StringBuilder();
        for (byte aByte : bytes) {
            stringBuilder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }

        return stringBuilder.toString();

    }

}
