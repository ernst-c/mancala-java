package mancala.persistence;

import mancala.domain.IMancala;
import java.util.HashMap;
import java.util.Map;

public class MancalaRepository implements IMancalaRepository {

    private final Map<String, IMancala> games = new HashMap<>();

    @Override
    public void save(String key, IMancala game) {
        games.put(key, game); // Store the game in the HashMap
    }
    @Override
    public IMancala get(String key) {
        return games.get(key); // Retrieve the game from the HashMap
    }}
