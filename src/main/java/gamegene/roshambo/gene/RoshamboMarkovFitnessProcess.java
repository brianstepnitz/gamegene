package gamegene.roshambo.gene;

import gamegene.gene.FitnessProcess;
import gamegene.gene.FitnessSortedPopulation;
import gamegene.roshambo.WeightedRoshamboPlayer;

import java.util.*;

public class RoshamboMarkovFitnessProcess implements FitnessProcess<WeightedRoshamboPlayer> {
    @Override
    public FitnessSortedPopulation<WeightedRoshamboPlayer> scoreFitnesses(List<WeightedRoshamboPlayer> population) {
        List<Double> winChanceMins = new ArrayList<>(population.size());
        for (WeightedRoshamboPlayer player : population) {
            winChanceMins.add(1.0);
        }
        for (ListIterator<WeightedRoshamboPlayer> forwardIter = population.listIterator(); forwardIter.hasNext(); ) {
            int player1Index = forwardIter.nextIndex();
            WeightedRoshamboPlayer player1 = forwardIter.next();

            for (ListIterator<WeightedRoshamboPlayer> reverseIter = population.listIterator(population.size()); reverseIter.previousIndex() != player1Index; ) {
                int player2Index = reverseIter.previousIndex();
                WeightedRoshamboPlayer player2 = reverseIter.previous();

                double rockChance1 = player1.getRockChance();
                double paperChance1 = player1.getPaperChance();
                double scissorsChance1 = player1.getScissorsChance();

                double rockChance2 = player2.getRockChance();
                double paperChance2 = player2.getPaperChance();
                double scissorsChance2 = player2.getScissorsChance();

                double winChance1 = (rockChance1 * scissorsChance2) + (paperChance1 * rockChance2) + (scissorsChance1 * paperChance2);
                double winChance2 = (rockChance2 * scissorsChance1) + (paperChance2 * rockChance1) + (scissorsChance2 * paperChance1);
                double tieChance = (rockChance1 * rockChance2) + (paperChance1 * paperChance2) + (scissorsChance1 * scissorsChance2);

                if (tieChance == 1.0) {
                    winChanceMins.set(player1Index, 0.0);
                    winChanceMins.set(player2Index, 0.0);
                }
                else {

                    double N = 1.0 / (1.0 - tieChance);

                    double matchWinChance1 = N * winChance1;
                    double winChanceMin1 = winChanceMins.get(player1Index);
                    winChanceMins.set(player1Index, Math.min(matchWinChance1, winChanceMin1));

                    double matchWinChance2 = N * winChance2;
                    double winChanceMin2 = winChanceMins.get(player2Index);
                    winChanceMins.set(player2Index, Math.min(matchWinChance2, winChanceMin2));
                }
            } // next reverseIter
        } // next forwardIter

        Map<WeightedRoshamboPlayer, Double> playerToMinWinChance = new HashMap<>();
        for (ListIterator<WeightedRoshamboPlayer> iter = population.listIterator(); iter.hasNext();) {
            double winChanceMin = winChanceMins.get(iter.nextIndex());
            WeightedRoshamboPlayer player = iter.next();

            playerToMinWinChance.put(player, winChanceMin);
        }
        return new FitnessSortedPopulation<>(playerToMinWinChance);
    }
}
