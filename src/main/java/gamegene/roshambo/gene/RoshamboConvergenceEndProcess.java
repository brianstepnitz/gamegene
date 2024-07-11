package gamegene.roshambo.gene;

import gamegene.gene.EndProcess;
import gamegene.gene.FitnessSortedPopulation;
import gamegene.gene.SelectionProcess;
import gamegene.roshambo.WeightedRoshamboPlayer;

import java.util.List;
import java.util.ListIterator;

public class RoshamboConvergenceEndProcess implements EndProcess<WeightedRoshamboPlayer> {

    private final SelectionProcess<WeightedRoshamboPlayer> selectionProcess;

    public RoshamboConvergenceEndProcess(SelectionProcess<WeightedRoshamboPlayer> selectionProcess) {
        if (selectionProcess != null) {
            this.selectionProcess = selectionProcess;
        }
        else {
            throw new IllegalArgumentException("selectionProcess must be nonnull");
        }
    }

    private int timesChecked = 0;

    @Override
    public boolean isEnd(FitnessSortedPopulation<WeightedRoshamboPlayer> fitnessSortedPopulation) {

        timesChecked++;
        System.out.println("timesChecked=" + timesChecked);

        List<WeightedRoshamboPlayer> selectedPopulation = fitnessSortedPopulation.select(this.selectionProcess);

        for (ListIterator<WeightedRoshamboPlayer> forwardIter = selectedPopulation.listIterator(); forwardIter.hasNext(); ) {
            WeightedRoshamboPlayer player1 = forwardIter.next();
            for (ListIterator<WeightedRoshamboPlayer> reverseIter = selectedPopulation.listIterator(selectedPopulation.size()); reverseIter.hasPrevious(); ) {
                WeightedRoshamboPlayer player2 = reverseIter.previous();
                if (player2 == player1) {
                    break;
                }
                if (player1.getRockWeight() != player2.getRockWeight() ||
                    player1.getPaperWeight() != player2.getPaperWeight() ||
                    player1.getScissorsWeight() != player2.getScissorsWeight()) {

                    return false;
                }
            }
        }

        return true;
    }
}
