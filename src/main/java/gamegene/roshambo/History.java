package gamegene.roshambo;

import java.util.LinkedList;

public class History {

    public static class Round {
        public Sign getPlayerOneSign() {
            return playerOneSign;
        }

        public Sign getPlayerTwoSign() {
            return playerTwoSign;
        }

        private final Sign playerOneSign;
        private final Sign playerTwoSign;

        public Round(Sign playerOneSign, Sign playerTwoSign) {
            this.playerOneSign = playerOneSign;
            this.playerTwoSign = playerTwoSign;
        }
    }

    private final LinkedList<Round> rounds;

    public History() {
        this.rounds = new LinkedList<>();
    }

    public void addRound(Sign sign1, Sign move2) {
        Round round = new Round(sign1, move2);
        this.rounds.add(round);
    }

    public LinkedList<Round> getRounds() {
        return this.rounds;
    }
}
