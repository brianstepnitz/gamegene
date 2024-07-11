package gamegene.roshambo.gene;

import gamegene.gene.FitnessProcess;
import gamegene.gene.FitnessSortedPopulation;
import gamegene.roshambo.History;
import gamegene.roshambo.RoshamboGame;
import gamegene.roshambo.Sign;
import gamegene.roshambo.WeightedRoshamboPlayer;

import java.util.*;

public class RoshamboTournamentFitnessProcess implements FitnessProcess<WeightedRoshamboPlayer> {

    private final int numGamesInMatch;
    private final int maxTies;

    public RoshamboTournamentFitnessProcess(int numGamesInMatch, int maxTies) {
        if (numGamesInMatch > 0 && maxTies > 0) {
            this.numGamesInMatch = numGamesInMatch;
            this.maxTies = maxTies;
        }
        else {
            throw new IllegalArgumentException("numGamesInMatch must be > 0: " + numGamesInMatch + " and maxTies must be > 0: " + maxTies);
        }
    }

    @Override
    public FitnessSortedPopulation<WeightedRoshamboPlayer> scoreFitnesses(List<WeightedRoshamboPlayer> players) {
        RoshamboGame roshambo = new RoshamboGame(this.maxTies);

        List<Double> playerWins = new ArrayList<>(players.size());
        for (WeightedRoshamboPlayer player : players) {
            playerWins.add(0.0);
        }
        for (ListIterator<WeightedRoshamboPlayer> forwardIter = players.listIterator(); forwardIter.hasNext();) {
            WeightedRoshamboPlayer player1 = forwardIter.next();
            for (ListIterator<WeightedRoshamboPlayer> reverseIter = players.listIterator(players.size()); reverseIter.previousIndex() >= forwardIter.nextIndex(); ) {
                WeightedRoshamboPlayer player2 = reverseIter.previous();

                for (int game = 0; game < this.numGamesInMatch; game++) {

                    History history = roshambo.play(new History(), player1, player2);

                    LinkedList<History.Round> rounds = history.getRounds();
                    History.Round lastRound = rounds.getLast();
                    Sign playerOneSign = lastRound.getPlayerOneSign();
                    Sign playerTwoSign = lastRound.getPlayerTwoSign();
                    if (playerOneSign != playerTwoSign) {

                        int winnerIndex;
                        if ((playerOneSign == Sign.ROCK && playerTwoSign == Sign.SCISSORS) ||
                                (playerOneSign == Sign.SCISSORS && playerTwoSign == Sign.PAPER) ||
                                (playerOneSign == Sign.PAPER && playerTwoSign == Sign.ROCK)) {

                           winnerIndex = forwardIter.previousIndex();
                        } else {
                            winnerIndex = reverseIter.nextIndex();
                        }

                        Double prevWins = playerWins.get(winnerIndex);
                        if (prevWins == null) {
                            prevWins = 0.0;
                        }
                        playerWins.set(winnerIndex, prevWins + 1.0);
                    }
                } // next game
            } // next player2
        } // next player1

        Map<WeightedRoshamboPlayer, Double> playerScoreMap = new HashMap<>();
        for (ListIterator<WeightedRoshamboPlayer> playerIter = players.listIterator(); playerIter.hasNext(); ) {
            int index = playerIter.nextIndex();
            WeightedRoshamboPlayer player = playerIter.next();

            playerScoreMap.put(player, playerWins.get(index));
        }

        return new FitnessSortedPopulation<>(playerScoreMap);
    }
}
