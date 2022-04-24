package fr.theome.unipass.manager;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author TheoM
 * @github github.com/TheoM-e
 * @details : KeyManager generate key from parameters and hashedToken that is converted to QrCode
 */

public class KeyManager {

    private final DBManager dbManager = new DBManager();
    private final TokenManager tokenManager = new TokenManager();

    /**
     * @return f92f0572f9f5684cee3e6576fd9d8d58684eea031ce4773d1e61725ce22674e1:1baea9858c94141500841171935c5be6:378cee02ef10dac9d0a9f8251da6b0b177e3e41e1bb72c941202c06278ad4b9f
     * @details string that'll be returned by the QrCode
     * */

    public String getKeyFromTokens(String key, Object value){

        HashMap<String, Object> mapOfDoc = dbManager.getValuesToMap(key, value);
        ArrayList<String> futureKeyList = new ArrayList<>();
        StringBuilder finalKey = new StringBuilder();

        String salt = "";

        for (Map.Entry<String, Object> entry : mapOfDoc.entrySet()){

            if (entry.getKey().equals("salt")){
                salt = entry.getValue().toString();
            }

            if (Arrays.asList("lastToken", "mirrorToken", "lastUpdate").contains(entry.getKey())){
                if (!entry.getKey().equals("lastUpdate")){
                    futureKeyList.add(entry.getValue().toString());
                } else {
                    futureKeyList.add(tokenManager.encodedBySalt(entry.getValue().toString(), tokenManager.stringToByte(salt)));
                }
            }
        }

        for (String string : futureKeyList){
            if(futureKeyList.indexOf(string) != futureKeyList.size()-1){
                finalKey.append(string).append(":");
            } else {
                finalKey.append(string);
            }
        }

        return finalKey.toString();

    }

}