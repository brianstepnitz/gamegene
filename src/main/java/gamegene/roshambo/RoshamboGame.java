package gamegene.roshambo;

import gamegene.game.SimultaneousTwoPlayerGame;

import java.util.LinkedList;

public class RoshamboGame implements SimultaneousTwoPlayerGame<History, Sign> {

    private final int maxTies;

    public RoshamboGame(int maxTies) {
        if (maxTies > 0) {
            this.maxTies = maxTies;
        }
        else {
            throw new IllegalArgumentException("maxTies must be > 0: " + maxTies);
        }
    }

    @Override
    public History nextGameState(History currentGameState, Sign playerOneMove, Sign playerTwoMove) {
        currentGameState.addRound(playerOneMove, playerTwoMove);

        return currentGameState;
    }

    @Override
    public boolean isEndState(History history) {
        LinkedList<History.Round> rounds = history.getRounds();
        if (rounds.isEmpty()) return false;

        History.Round lastRound = rounds.getLast();
        if (lastRound.getPlayerOneSign() != lastRound.getPlayerTwoSign()) {
            return true;
        }
        else {
            return rounds.size() >= this.maxTies;
        }
    }
}
