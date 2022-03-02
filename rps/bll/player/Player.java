package rps.bll.player;

//Project imports
import rps.bll.game.IGameState;
import rps.bll.game.Move;
import rps.bll.game.Result;

//Java imports
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Example implementation of a player.
 *
 * @author smsj
 */
public class Player implements IPlayer {

    private String name;
    private PlayerType type;

    private int[][] markovChain;
    private int lastMove, lastLastMove;

    /**
     * @param name
     */
    public Player(String name, PlayerType type) {
        this.name = name;
        this.type = type;
        newGame();
    }

    private void newGame() {
        markovChain = new int[3][3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                markovChain[i][j] = 0;
            }
        }
        lastMove = -1;
        lastLastMove = -1;
    }


    @Override
    public String getPlayerName() {
        return name;
    }


    public void setPlayerName(String name){
        this.name = name;
    }


    @Override
    public PlayerType getPlayerType() {
        return type;
    }


    /**
     * Decides the next move for the bot...
     * @param state Contains the current game state including historic moves/results
     * @return Next move
     */
    @Override
    public Move doMove(IGameState state) {
        Move returnMove;
        ArrayList<Result> results = (ArrayList<Result>) state.getHistoricResults();

        lastLastMove = lastMove;

        if(results.size() > 0){
            Result lastResult = results.get(results.size() -1);
            lastMove = determineLastMove(lastResult.getHumanMove());
        }

        if(lastLastMove >= 0){
            markovChain[lastLastMove][lastMove] += 1;

            int[] counts = markovChain[lastMove];
            if (counts[0] > counts[1] || counts[0] > counts[2]){
                return Move.Paper;
            }
            else if (counts[1] > counts[2]){
                return Move.Scissor;
            }
            else
                return Move.Rock;
        }
        else {
            Random rand = new Random();
            int pick = rand.nextInt(3);
            if (pick == 0)
                return Move.Rock;
            else if (pick == 1)
                return Move.Paper;
            else
                return Move.Scissor;
        }
    }


    private int determineLastMove(Move humanMove) {
        if(humanMove == Move.Rock)
            return 0;
        else if (humanMove == Move.Paper)
            return 1;
        else
            return 2;
    }
}
