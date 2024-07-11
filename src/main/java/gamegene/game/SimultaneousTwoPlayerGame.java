package gamegene.game;

public interface SimultaneousTwoPlayerGame<GameState, Move> {

    default GameState play(GameState initialGameState, Player<GameState, Move> player1, Player<GameState, Move> player2) {
        GameState gameState = initialGameState;

        while (isEndState(gameState) == false) {
            Move move1 = player1.chooseMove(gameState);
            Move move2 = player2.chooseMove(gameState);

            gameState = nextGameState(gameState, move1, move2);
        }

        return gameState;
    }

    GameState nextGameState(GameState currentGameState, Move playerOneMove, Move playerTwoMove);

    boolean isEndState(GameState gameState);
}
