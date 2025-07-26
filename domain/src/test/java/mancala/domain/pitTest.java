package mancala.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//create pit to before each
//change to gamestate 
public class pitTest {
    @Test
    public void checkIfPitHas4Seeds() {
        NormalPit pit = new NormalPit();
        assertEquals(4,pit.getSeedCount());
    }
    @Test
    public void checkIfPitHasConstructorWithInputArguments() {
        NormalPit initialPit = new NormalPit();
        Player p1 = new Player();
        int pitNr = 0;
        Pit pit = new NormalPit(initialPit,p1,pitNr);
    }
    @Test
    public void checkIfPitWithConstructorHas4Seeds() {
        NormalPit initialPit = new NormalPit();
        Player p1 = new Player();
        int pitNr = 0;
        Pit pit = new NormalPit(initialPit,p1,pitNr);
        assertEquals(4,pit.getSeedCount());
    }
    @Test
    public void checkIfPitHasOwner() {
        Pit initialPit = new NormalPit();
        Player owner = initialPit.getOwner();
    }
    @Test
    public void checkIfOwnerIsNotNull() {
        NormalPit initialPit = new NormalPit();
        Player owner = initialPit.getOwner();
        assertNotNull(owner);
    }
    @Test
    public void checkIfPitWithConstructorOwnerIsNotNull() {
        NormalPit initialPit = new NormalPit();
        Player player = new Player();
        int pitNr = 0;
        Pit pit = new NormalPit(initialPit,player,pitNr);
        assertNotNull(pit.getOwner());
    }
    @Test
    public void checkIfPitWithConstructorOwnerIsInputPlayer() {
        NormalPit initialPit = new NormalPit();
        Player owner = new Player();
        int pitNr = 0;
        Pit pit = new NormalPit(initialPit,owner,pitNr);
        assertEquals(owner, pit.getOwner());
    }
    @Test
    public void checkIfPitWithConstructorHasNeighborPitProperty() {
        NormalPit initialPit = new NormalPit();
        Player owner = new Player();
        int pitNr = 0;
        Pit pit = new NormalPit(initialPit,owner,pitNr);
        Pit neighbor = pit.getNeighbor();
    }
    @Test
    public void checkIfNeighborPitPropertyIsNotNull() {
        NormalPit initialPit = new NormalPit();
        Player owner = new Player();
        int pitNr = 0;
        NormalPit pit = new NormalPit(initialPit,owner,pitNr);
        Pit neighbor = pit.getNeighbor();
        assertNotNull(neighbor);
    }
    @Test
    public void checkIfPitHasPitNrProperty() {
        NormalPit pit = new NormalPit();
        int pitNr = pit.getPitNr();
    }
    @Test
    public void checkIfPitNrEqualsInputPitNr() {
        NormalPit initialPit = new NormalPit();
        Player owner = new Player();
        int pitNr = 5;
        NormalPit pit = new NormalPit(initialPit,owner,pitNr);
        assertEquals(pitNr,pit.getPitNr());
    }
    @Test
    public void checkIfPitCreatesNeighborPitWithConstructor() {
        NormalPit initialPit = new NormalPit();
        Pit neighbor = initialPit.getNeighbor();
        assertNotNull(neighbor);
    }
    @Test
    public void checkIfSeventhPitIsKalahaPit() {
        NormalPit initialPit = new NormalPit();
        Player owner = new Player();
        int pitNr = 6;
        NormalPit pit = new NormalPit(initialPit,owner,pitNr);
        assertInstanceOf(KalahaPit.class, pit.getNeighbor());
    }
    @Test
    public void checkIfFourteenthPitIsKalahaPit() {
        NormalPit initialPit = new NormalPit();
        Player owner = new Player();
        int pitNr = 13;
        NormalPit pit = new NormalPit(initialPit,owner,pitNr);
        assertInstanceOf(KalahaPit.class, pit.getNeighbor());
    }
    @Test
    public void checkIfFirstKalahaPitHasNeighbor() {
        NormalPit initialPit = new NormalPit();
        Player owner = new Player();
        int pitNr = 6;
        NormalPit pit = new NormalPit(initialPit,owner,pitNr);
        Pit kalahaPit = pit.getNeighbor();
        Pit neighbor = kalahaPit.getNeighbor();
        assertNotNull(neighbor);
    }
    @Test
    public void checkIfSecondKalahaPitHasNeighborInitialPit() {
        NormalPit initialPit = new NormalPit();
        Player owner = new Player();
        int pitNr = 13;
        Pit pit = new NormalPit(initialPit,owner,pitNr);
        Pit kalahaPit = pit.getNeighbor();
        Pit neighbor = kalahaPit.getNeighbor();
        assertEquals(neighbor, initialPit);
    }
    @Test
    public void checkIfDoMoveEmptiesPit() {
        NormalPit pit = new NormalPit();
        pit.doMove();
        assertEquals(0,pit.getSeedCount());
    }
    @Test
    public void checkIfDoMoveIncreasesNeighborSeedCount() {
        NormalPit pit = new NormalPit();
        pit.doMove();
        assertEquals(5,pit.getNeighbor().getSeedCount());
    }
    @Test
    public void checkIfDoMoveIncreaseNeighborsNeighborSeedCount() {
        NormalPit pit = new NormalPit();
        pit.doMove();
        assertEquals(5,pit.getNeighbor().getNeighbor().getSeedCount());
    }
    @Test
    public void checkIfTransferToKalahaMethodEmptiesPit() {
        NormalPit pit = new NormalPit();
        pit.transferToKalaha(pit.getSeedCount());
        assertEquals(0,pit.getSeedCount());
    }
    @Test
    public void checkIfTransferToKalahaMethodIncreasesKalahaSeedCount() {
        NormalPit pit = new NormalPit();
        pit.transferToKalaha(pit.getSeedCount());
        assertEquals(4, pit.findNeighborFromPitNr(7).getSeedCount());
    }
    @Test
    public void checkIfOwnerIsCorrect() {
        Pit pit = new NormalPit();
        Player owner1 = pit.findNeighborFromPitNr(1).getOwner();
        Player owner2 = pit.findNeighborFromPitNr(8).getOwner();
        for (int i = 1; i< 14; i++) {
            if (i <= 7) {
                assertEquals(owner1, pit.findNeighborFromPitNr(i).getOwner());
            } else {
                assertEquals(owner2, pit.findNeighborFromPitNr(i).getOwner());
            }
        }
        assertNotEquals(owner1,owner2);
    }
    @Test
    public void checkIfPitNrAreCorrect() {
        Pit pit = new NormalPit();
        for (int i = 1; i<15; i++) {
            assertEquals(i, pit.findNeighborFromPitNr(i).getPitNr());
        }
    }
    @Test
    public void checkIfFindOpponentFindsOpponent() {
        NormalPit pit = new NormalPit();
        NormalPit opponentPit = pit.findOpponent();
        assertEquals(pit.getPitNr(),(14-opponentPit.getPitNr()));
    }
    @Test
    public void checkIfStealEmptiesOpponentPit() {
        NormalPit pit = new NormalPit();
        Pit opponentPit = pit.findOpponent();
        pit.steal();
        assertEquals(0,opponentPit.getSeedCount());
    }
    @Test
    public void checkIfStealAddsSeeds() {
        NormalPit pit = new NormalPit();
        pit.steal();
        assertEquals(8,pit.getSeedCount());
    }
    @Test
    public void checkCheckEmptySide() {
        NormalPit pit = new NormalPit();
        for (int i=8;i<14;i++) {
            ((NormalPit) pit.findNeighborFromPitNr(i)).steal();
        }
        assertTrue(pit.findNeighborFromPitNr(1).checkEmptySide());
    }
    @Test
    public void checkIfCheckEmptySideOnlyWorksForEntireSide() {
        NormalPit pit = new NormalPit();
        for (int i=8;i<13;i++) {// skip the last steal to create incorrect scenario

            ((NormalPit) pit.findNeighborFromPitNr(i)).steal();
        }
        assertFalse(pit.findNeighborFromPitNr(2).checkEmptySide());
    }
    @Test
    public void checkIfCheckForWinnerChecksKalaha() {
        NormalPit pit = new NormalPit();
        for (int i=8;i<14;i++) {
            ((NormalPit) pit.findNeighborFromPitNr(i)).steal();
            ((NormalPit) pit.findNeighborFromPitNr(i)).transferToKalaha(pit.findNeighborFromPitNr(i).getSeedCount());
        }
        assertTrue(pit.checkForWinner());
    }
    @Test
    public void checkIfReturnWinnerReturnsPlayer() {
        Pit pit = new NormalPit();
        for (int i=8;i<14;i++) {
            ((NormalPit) pit.findNeighborFromPitNr(i)).steal();
            ((NormalPit) pit.findNeighborFromPitNr(i)).transferToKalaha(pit.findNeighborFromPitNr(i).getSeedCount());
        }
        pit.returnWinner();
    }
    @Test
    public void checkIfReturnWinnerReturnsCorrectPlayer() {
        Pit pit = new NormalPit();
        for (int i=8;i<14;i++) {
            ((NormalPit) pit.findNeighborFromPitNr(i)).steal();
            ((NormalPit) pit.findNeighborFromPitNr(i)).transferToKalaha(pit.findNeighborFromPitNr(i).getSeedCount());
        }
        Player winner = pit.findNeighborFromPitNr(1).returnWinner();
        assertEquals(winner, pit.findNeighborFromPitNr(8).getOwner());
    }
    @Test
    public void checkIsTurn() {
        Pit pit = new NormalPit();
        boolean turn = pit.getOwner().isTurn();
    }
    @Test
    public void checkBothPlayersDoNotHaveTurn() {
        Pit pit = new NormalPit();
        boolean turnPlayerOne = pit.findNeighborFromPitNr(1).getOwner().isTurn();
        boolean turnPlayerTwo = pit.findNeighborFromPitNr(8).getOwner().isTurn();
        assertNotEquals(turnPlayerOne,turnPlayerTwo);
    }
    @Test
    public void checkIfTurnSwitchesAfterDoMove() {
        NormalPit pit = new NormalPit();
        boolean turn1 = pit.findNeighborFromPitNr(1).getOwner().isTurn();
        pit.doMove();
        boolean turn2 = pit.findNeighborFromPitNr(1).getOwner().isTurn();
        assertNotEquals(turn1,turn2);
    }
    @Test
    public void checkIfOtherPlayersTurnAlsoSwitchesAfterMove() {
        NormalPit pit = new NormalPit();
        boolean turn1 = pit.findNeighborFromPitNr(8).getOwner().isTurn();
        pit.doMove();
        boolean turn2 = pit.findNeighborFromPitNr(8).getOwner().isTurn();
        assertNotEquals(turn1,turn2);
    }
    @Test
    public void checkIfDoMoveReturnsExceptionIfPitIsEmpty() {
        NormalPit pit = new NormalPit();
        pit.doMove();
        invalidMoveException exception = assertThrows(invalidMoveException.class, pit::doMove);
        assertEquals("Cannot make a move with seed count = 0", exception.getMessage());
    }
    @Test
    public void checkIfPitsAreEmptyAfter1SeedLandsOnEmptyPit() {
        NormalPit pit = new NormalPit();
        ((NormalPit) pit.findNeighborFromPitNr(6)).doMove();
        ((NormalPit) pit.findNeighborFromPitNr(10)).doMove();
        ((NormalPit) pit.findNeighborFromPitNr(2)).doMove();
        assertEquals(0,pit.findNeighborFromPitNr(6).getSeedCount());
        assertEquals(0,pit.findNeighborFromPitNr(8).getSeedCount());
    }
    @Test
    public void checkIfKalahaPitIsFilledAfter1SeedLandsOnEmptyPit() {
        NormalPit pit = new NormalPit();
        ((NormalPit) pit.findNeighborFromPitNr(6)).doMove();
        ((NormalPit) pit.findNeighborFromPitNr(10)).doMove();
        ((NormalPit) pit.findNeighborFromPitNr(2)).doMove();
        assertEquals(7,pit.findNeighborFromPitNr(7).getSeedCount());
    }
    @Test
    public void checkIfPlayerTurnRemainsTheSameIfLastSeedLandsOnKalaha() {
        NormalPit pit = new NormalPit();
        boolean currentTurnPlayerOne = pit.findNeighborFromPitNr(1).getOwner().isTurn();
        ((NormalPit) pit.findNeighborFromPitNr(3)).doMove();
        assertEquals(currentTurnPlayerOne,pit.findNeighborFromPitNr(1).getOwner().isTurn());
    }
    @Test
    public void checkIfdoMoveThrowsExceptionIfPlayerTurnIsFalse() {
        NormalPit pit = new NormalPit();
        invalidMoveException exception = assertThrows(invalidMoveException.class, ((NormalPit) pit.findNeighborFromPitNr(8))::doMove);
        assertEquals("Pit belongs to different player", exception.getMessage());
    }
    @Test
    public void checkIfNeighborIsCorrectlySet() {
        Pit pit = new NormalPit();
        for (int i = 1; i < 15; i++) {
            assertEquals(pit.getPitNr(),i);
            pit = pit.getNeighbor();
        }
    }
}
