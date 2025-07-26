package mancala.domain;

public class NormalPit extends Pit {

    public NormalPit() {
        super();
        this.seedCount = 4;
        owner = new Player();
        this.pitNr = 1;
        this.neighbor = new NormalPit(this, owner, this.pitNr+1);
    }
    public NormalPit(NormalPit initialPit, Player currentPlayer, int pitNr) {
        this.seedCount = 4;
        owner = currentPlayer;
        this.pitNr = pitNr;
        if (this.pitNr == 6 || this.pitNr == 13) {
            neighbor = new KalahaPit(initialPit, owner, this.pitNr + 1);
        } else {
            neighbor = new NormalPit(initialPit, owner, this.pitNr + 1);
        }
    }
    public Pit getNeighbor() {
        return neighbor;
    }
    public void addSeeds(int seedCount) {
        this.seedCount += seedCount;
    }
    public void removeSeeds(int seedCount) {
        this.seedCount -= seedCount;
    }
    public void doMove() {
        int seeds = seedCount;
        if (seedCount ==0) {
            throw new invalidMoveException("Cannot make a move with seed count = 0");
        } else if (!this.getOwner().isTurn()) {
            throw new invalidMoveException("Pit belongs to different player");
        }
        this.removeSeeds(seeds);
        this.neighbor.keepOneSeedMoveRest(seeds);
        this.owner.changeTurnBothPlayers();
    }
    public NormalPit findOpponent() {
        return (NormalPit) findNeighborFromPitNr(14-this.pitNr);
    }
    private boolean stealCondition() {
        return (getSeedCount() == 1);
    }
    public void keepOneSeedMoveRest(int seeds){
        if (seeds > 1) {
            seedCount++;
            getNeighbor().keepOneSeedMoveRest(seeds-1);
        } else if (seeds == 1) {
            seedCount++;
            if (stealCondition() && getOwner().isTurn()) {
                steal();
                transferToKalaha(seedCount);
            }
        }
    }
    public void steal() {
        NormalPit opponent = this.findOpponent();
        int opponentSeeds = opponent.getSeedCount();
        opponent.removeSeeds(opponentSeeds);
        this.addSeeds(opponentSeeds);
    }
    public KalahaPit getPlayerKalaha(Player owner) {
        if (this.findNeighborFromPitNr(7).getOwner() == owner) {
            return (KalahaPit) this.findNeighborFromPitNr(7);
        } else {
            return (KalahaPit) this.findNeighborFromPitNr(14);
        }
    }
    public void transferToKalaha(int seeds) {
            removeSeeds(seeds);
            this.getPlayerKalaha(this.getOwner()).addSeeds(seeds);
    }
}
