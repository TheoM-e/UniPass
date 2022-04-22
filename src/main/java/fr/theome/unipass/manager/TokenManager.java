package fr.theome.unipass.manager;

import org.apache.commons.lang.RandomStringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author TheoM
 * @github github.com/TheoM-e
 */

public class TokenManager {

    public String getRandomToken(){
        return RandomStringUtils.randomAlphanumeric(32);
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
