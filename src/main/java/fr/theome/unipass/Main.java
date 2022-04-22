package fr.theome.unipass;

import fr.theome.unipass.manager.DBManager;
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
        String token = tokenManager.getRandomToken();

        dbManager.insertDocument(
                        token, tokenManager.getHashedToken(token), true,
                new ArrayList<>(
                        Arrays.asList(
                                new HashMap<String, String>() {{
                                    put("LastUpdate", String.valueOf(new Date().getTime() / 1000));
                                }},
                                new HashMap<String, String>() {{
                                    put("Use", "3");
                                }})
                )
        );

        System.out.println(dbManager.getDoc("_id", "HDNrOmWdYC7Y5nzeBIgqBg4xcuthTK1S"));

        //{SuSKbm3OVtPVUYMVaiqW8Iuf8ECXMKhY: {lastToken: SuSKbm3OVtPVUYMVaiqW8Iuf8ECXMKhY, isAvailable: true, use: 3, lastUpdate: 1650644570}}


    }

}
