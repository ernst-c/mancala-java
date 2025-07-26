package mancala.domain;

public class NormalPit extends Pit {

    public NormalPit() {
        super();
        this.seedCount = 4;
        owner = new Player();
        this.pitNr = 0;
        this.neighbor = new NormalPit(this, owner, this.pitNr+1);
        this.gameState = "";
    }
    public NormalPit(NormalPit initialPit, Player currentPlayer, int pitNr) {
        this.seedCount = 4;
        owner = currentPlayer;
        this.pitNr = pitNr;
        if (this.pitNr == 5 || this.pitNr == 12) {
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
        this.addMoveToGameState();
    }
    public void addMoveToGameState() {
        findNeighborFromPitNr(0).gameState += this.getPitNr()+"_";
    }
    public String returnGameState() {
        return ((NormalPit) this.findNeighborFromPitNr(0)).getGameState();
    }
    public String getGameState() {
        return this.gameState;
    }

    public NormalPit findOpponent() {
        return (NormalPit) findNeighborFromPitNr(13-this.pitNr);
    }
    private boolean stealCondition() {
        return (getSeedCount() == 1);
    }
    public void keepOneSeedMoveRest(int seeds) {
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
        if (this.findNeighborFromPitNr(6).getOwner() == owner) {
            return (KalahaPit) this.findNeighborFromPitNr(6);
        } else {
            return (KalahaPit) this.findNeighborFromPitNr(13);
        }
    }
    public void transferToKalaha(int seeds) {
            removeSeeds(seeds);
            this.getPlayerKalaha(this.getOwner()).addSeeds(seeds);
    }
    public void playGameState(String gamestate) {
        String[] split = gamestate.split("_");
        for (int i = 2; i < split.length; i++) {
            if (!split[i].isEmpty()) {
                ((NormalPit) this.findNeighborFromPitNr(Integer.parseInt(split[i]))).doMove();
            }
        }
    }
}
