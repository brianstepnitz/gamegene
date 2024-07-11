package gamegene.roshambo;

import java.util.Random;

public class RandomRoshamboPlayer implements RoshamboPlayer {

    private final Random random;

    public RandomRoshamboPlayer(Random rng) {
        this.random = rng;
    }

    @Override
    public Sign chooseMove(History history) {
        Sign[] signs = Sign.values();

        return signs[random.nextInt(signs.length)];
    }
}
