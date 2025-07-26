package mancala.domain;

import static java.lang.Math.abs;
/*
todo:
- gebruik niet harde connecties tussen pits, verwijs objecten door
- geen loops in test, gebruik gamestate
- UI meer scheiden van logica --> done
- niet in abstract zetten, casten naar normalpit of kalahapit
- player vanuit player constructen --> done
- findkalahaPit(player) functie implementeren? --> done
 */
public abstract class Pit {
    Player owner;
    Pit neighbor;
    int pitNr;
    int seedCount;

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
        if (this.getPitNr() <= 7) {
            return this.findNeighborFromPitNr(1).checkIfAllPitsOneSideEmpty();
        } else {
            return this.findNeighborFromPitNr(8).checkIfAllPitsOneSideEmpty();
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
        return (this.findNeighborFromPitNr(1).checkEmptySide() || this.findNeighborFromPitNr(8).checkEmptySide());
    }
    private int getAmountOfStonesPerSide() {
        if (this instanceof NormalPit) {
            return getNeighbor().getSeedCount()+getNeighbor().getAmountOfStonesPerSide();
        } else {
            return getSeedCount();
        }
    }
    public Player returnWinner() {
        if (findNeighborFromPitNr(1).getAmountOfStonesPerSide() > findNeighborFromPitNr(8).getAmountOfStonesPerSide()) {
            return findNeighborFromPitNr(1).getOwner();
        } else {
            return findNeighborFromPitNr(8).getOwner();
        }
    }
}

