package mancala.domain;

public class KalahaPit extends Pit {

    public KalahaPit() {
    }
    public KalahaPit(NormalPit initialPit, Player currentPlayer, int pitNr) {
        super();
        seedCount = 0;
        owner = currentPlayer;
        this.pitNr = pitNr;
        if (pitNr == 13) {
            neighbor = initialPit;
        } else {
            neighbor = new NormalPit(initialPit, owner.getOpposingPlayer(), this.pitNr + 1);
        }
    }
    public NormalPit getNeighbor() {
        return (NormalPit) neighbor;
    }
    public void addSeeds(int seedCount) {
        this.seedCount += seedCount;
    }
    public void keepOneSeedMoveRest(int seeds){
        if (seeds > 1) {
            seedCount++;
            getNeighbor().keepOneSeedMoveRest(seeds-1);
        } else if (seeds == 1) {
            seedCount++;
            if(getOwner().isTurn()) {
                getOwner().changeTurnBothPlayers();
            }
        }
    }
}
