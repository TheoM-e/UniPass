package fr.theome.unipass.manager;

import javax.crypto.SecretKeyFactory;
import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Random;

/**
 * @author TheoM
 * @github github.com/TheoM-e
 */

public class TokenManager {

    private static final Random RANDOM = new SecureRandom();

    public String getRandomToken(){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(32);

        for (int i = 0; i < 32; i++) {
            double index = (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(((int) index)));
        }

        return sb.toString();
    }


    public byte[] getNextSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    public String byteToString(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }

    public byte[] stringToByte(String string){
        return Base64.getDecoder().decode(string);
    }

    public String encodedBySalt(String token, byte[] salt){

        String hashedToken = null;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(token.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte aByte : bytes) {
                stringBuilder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            hashedToken = stringBuilder.toString();
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return hashedToken;
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
