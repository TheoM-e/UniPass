package fr.theome.unipass.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author TheoM
 * @github github.com/TheoM-e
 */

public class DBManager {

    private final FileManager fileManager = new FileManager();

    private final MongoClientURI URI = new MongoClientURI(String.format("mongodb://%s/", fileManager.getFirstLine("src/main/java/fr/theome/unipass/keys/cred.key")));
    private final MongoClient mongoClient = new MongoClient(URI);

    private final MongoDatabase dataBase = mongoClient.getDatabase("unipassdb");
    private final MongoCollection<Document> collection = dataBase.getCollection("ParamByToken");

    /**
     * @parameters ex : [{"Use": 3}, {"LastUpdate": 99999999} ...]
     * @LastUpdate : new Date().getTime()/1000 -> seconds
     * */
    public void insertDocument(@NotNull String token,
                               @NotNull String mirrorToken,
                               @NotNull String salt,
                               @NotNull String lastTokenValue,
                               @NotNull Boolean isAvailable,
                               @Nullable List<HashMap<String,String>> parameters){

        Document TokenDoc = new Document("_id", token);
        TokenDoc.append("mirrorToken", mirrorToken)
                .append("salt", salt)
                .append("lastToken", lastTokenValue)
                .append("isAvailable", isAvailable);

        if (parameters != null && !parameters.isEmpty()){
            for (HashMap<String, String> map : parameters){
                if (map != null && !map.isEmpty()){
                    Map.Entry<String, String> entry = map.entrySet().iterator().next();
                    if(entry.getKey() != null && entry.getValue() != null){
                        TokenDoc.append(entry.getKey(), entry.getValue());
                    }
                }
            }
        }

        collection.insertOne(TokenDoc);

    }

    public String getDocToStr(String key, Object value){
        Document doc = collection.find(eq(key, value)).first();
        if (doc == null) { return null; }
        return doc.toJson();
    }

    public void updateDoc(String key, Object value, String newKey, Object newValue){
        collection.updateOne(eq(key, value), Updates.set(newKey, newValue));
    }

    public HashMap<String, Object> getValuesToMap (String key, Object value){
        ObjectMapper mapper = new ObjectMapper();

        try{
            return mapper.readValue(getDocToStr(key, value), HashMap.class);
        } catch (IOException e){
            return null;
        }
    }

}