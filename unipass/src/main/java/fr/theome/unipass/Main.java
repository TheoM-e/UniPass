package fr.theome.unipass;

import fr.theome.unipass.manager.DBManager;
import fr.theome.unipass.manager.KeyManager;
import fr.theome.unipass.manager.TokenManager;

import java.util.*;

/**
 * @author TheoM
 * @github github.com/TheoM-e
 */

public class Main {

    public static void main(String[] args) {

        DBManager dbManager = new DBManager();
        TokenManager tokenManager = new TokenManager();
        KeyManager keyManager = new KeyManager();

        String token = tokenManager.getRandomToken();
        String mirrorToken = tokenManager.getHashedToken(tokenManager.getRandomToken());
        String salt = tokenManager.byteToString(tokenManager.getNextSalt());
        
        dbManager.insertDocument(
                token, mirrorToken, salt, tokenManager.encodedBySalt(token, tokenManager.stringToByte(salt)), true,
                new ArrayList<>(
                        Arrays.asList(
                                new HashMap<String, String>() {{
                                    put("lastUpdate", String.valueOf(new Date().getTime() / 1000));
                                }},
                                new HashMap<String, String>() {{
                                    put("use", "3");
                                }})
                )
        );
    }
}
