package mancala.domain;

public class Mancala implements IMancala {
    NormalPit game;
    String gameState = "_";

    public Mancala(String namePlayerOne, String namePlayerTwo) {
        this.game = new NormalPit();
        this.game.owner.setName(namePlayerOne);
        this.game.owner.getOpposingPlayer().setName(namePlayerTwo);
        this.gameState = namePlayerOne+"_"+namePlayerTwo+"_";
    }

    @Override
    public String getNameOfPlayerOne() {
        return game.owner.getName();
    }

    @Override
    public String getNameOfPlayerTwo() {
        return game.owner.getOpposingPlayer().getName();
    }

    @Override
    public boolean isPlayersTurn(String name) {
        return game.owner.returnPlayerFromName(name).isTurn();
    }

    @Override
    public void playPit(int index) {
        NormalPit neighborPit = (NormalPit) game.findNeighborFromPitNr(index);
        neighborPit.doMove();
    }

    @Override
    public int getStonesForPit(int index) {
        return game.findNeighborFromPitNr(index).getSeedCount();
    }

    @Override
    public boolean isEndOfGame() {
        return game.checkForWinner();
    }

    @Override
    public Winner getWinner() {
        String result = game.returnWinner();
        switch (result) {
            case "PLAYER_1":
                return Winner.PLAYER_1;
            case "PLAYER_2":
                return Winner.PLAYER_2;
            case "DRAW":
                return Winner.DRAW;
            case "NO_ONE":
                return Winner.NO_ONE;
        }
        return null;
    }
    public void playGameState(String gameState) {
        this.game.playGameState(gameState);
    }
    public String getGameState() {
            return this.gameState + game.returnGameState();
        //if (((NormalPit) this.game.findNeighborFromPitNr(1)).getGameState() == "null") {
        //    return this.gameState;
        //} else {
        //    return this.gameState + ((NormalPit) this.game.findNeighborFromPitNr(1)).getGameState();
        //}
    }
    //add String getGameState method
}
