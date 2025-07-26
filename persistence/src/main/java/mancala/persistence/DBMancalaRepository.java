package mancala.persistence;
import com.mongodb.client.model.ReplaceOptions;
import mancala.domain.IMancala;
import mancala.domain.Mancala;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DBMancalaRepository implements IMancalaRepository {
    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;
    private final Map<String, IMancala> games = new HashMap<>();

    public DBMancalaRepository() {
        mongoClient = MongoClients.create("mongodb://localhost:27017"); // Change as needed
        database = mongoClient.getDatabase("mancalaDB");
        collection = database.getCollection("games");
    }

    @Override
    public void save(String key, IMancala game) {

        String gameState = ((Mancala) game).getGameState();
        database.drop();
        MongoCollection<Document> collection = database.getCollection("games");

        Document doc = new Document("gameState", gameState);
        collection.insertOne(doc);    }

    @Override
    public IMancala get(String key) {
        Document doc = collection.find().first();

        if (doc != null) {
            String gameState = doc.getString("gameState");
            String[] split = gameState.split("_");

            Mancala game = new Mancala(split[0], split[1]);
            game.playGameState(gameState);
            return game;
        }

        return null;
    }
}

