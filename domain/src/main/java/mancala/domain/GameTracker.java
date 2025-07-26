package mancala.domain;

public class GameTracker {
    private String gameState;

    public GameTracker() {
        gameState = "";
    }
    public NormalPit playGameState(String savedGameState, NormalPit pit) {
        gameState = savedGameState;
        String [] split = savedGameState.split("_");
        for (String move : split) {
            ((NormalPit) pit.findNeighborFromPitNr(Integer.parseInt(move))).doMove();
        }
        return pit;
    }
    public void addMoveToGameState(int input) {
        this.gameState += input+"_";
    }
    public String returnGameState() {
        return gameState;
    }
}
