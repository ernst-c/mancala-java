package mancala.domain;

public abstract class Pit {
    Player owner;
    Pit neighbor;
    int pitNr;
    int seedCount;
    String gameState;


    public Pit() {}
    public abstract Pit getNeighbor();
    public int getSeedCount() {

        return seedCount;
    }
    public Player getOwner() {
        return owner;
    }
    public int getPitNr() {
        return pitNr;
    }
    public abstract void keepOneSeedMoveRest(int seeds);

    boolean checkEmptySide() {
        if (this.getPitNr() <= 6) {
            return this.findNeighborFromPitNr(0).checkIfAllPitsOneSideEmpty();
        } else {
            return this.findNeighborFromPitNr(7).checkIfAllPitsOneSideEmpty();
        }
    }
    private boolean checkIfAllPitsOneSideEmpty() {
        if (this.getNeighbor() instanceof NormalPit) {
            if (this.getSeedCount() == 0) {
                return this.getNeighbor().checkIfAllPitsOneSideEmpty();
            } else {
                return false;
            }
        } else {
            return (this.getSeedCount() == 0);
        }
    }
    public Pit findNeighborFromPitNr(int pitNr) {
        if (neighbor.getPitNr() == pitNr) {
            return neighbor;
        } else {
            return neighbor.findNeighborFromPitNr(pitNr);
        }
    }
    public boolean checkForWinner() {
        return (this.findNeighborFromPitNr(0).checkEmptySide() || this.findNeighborFromPitNr(7).checkEmptySide());
    }
    private int getAmountOfStonesPerSide() {
        if (this instanceof NormalPit) {
            return getNeighbor().getSeedCount()+getNeighbor().getAmountOfStonesPerSide();
        } else {
            return getSeedCount();
        }
    }
    public String returnWinner() {
        if (findNeighborFromPitNr(0).getAmountOfStonesPerSide() > findNeighborFromPitNr(7).getAmountOfStonesPerSide()) {
            return "PLAYER_1";
        } else if (findNeighborFromPitNr(0).getAmountOfStonesPerSide() < findNeighborFromPitNr(7).getAmountOfStonesPerSide()) {
            return "PLAYER_2";
        } else if (!this.checkForWinner()) {
            return "NO_ONE";
        }
        else {
            return "DRAW";
        }
    }
}

