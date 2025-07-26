package mancala.domain;
public class Player {
    private boolean turn = true;
    private Player otherPlayer;
    public Player() {
        this.storeOtherPlayer(new Player(this));
    }
    public Player(Player player) {
        turn = false;
        this.storeOtherPlayer(player);
    }
    public void storeOtherPlayer(Player otherPlayer) {
        this.otherPlayer = otherPlayer;
    }
    public Player getOpposingPlayer() {
        return this.otherPlayer;
    }
    void changeTurnBothPlayers() {
        changeTurn();
        this.otherPlayer.changeTurn();
    }
    private void changeTurn() {
        turn = !turn;
    }
    public boolean isTurn() {
        return turn;
    }
}