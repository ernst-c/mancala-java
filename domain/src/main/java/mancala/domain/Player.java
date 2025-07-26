package mancala.domain;
public class Player {
    private boolean turn = true;
    private Player otherPlayer;
    private String name;
    public Player() {
        this.storeOtherPlayer(new Player(this));
    }
    public Player(Player player) {
        turn = false;
        this.storeOtherPlayer(player);
    }
    public void setName(String name) {
        this.name = name;
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
    public String getName() {
        return this.name;
    }
    public Player returnPlayerFromName(String name) {
        if (name.equals(this.getName())) {
            return this;
        } else if (name.equals(this.getOpposingPlayer().getName())) {
            return this.getOpposingPlayer();
        }
        return null;
    }
}