package rps.bll.game;

// Project imports
import rps.bll.player.IPlayer;

/**
 * Defines a Result in the game
 *
 * @author smsj
 */
public class Result {
    private ResultType type;
    private IPlayer winnerPlayer;
    private IPlayer loserPlayer;
    private Move humanMove;
    private Move botMove;
    private int roundNumber;


    /**
     * Initializes a new Result with a winner, loser, their moves and a type
     * @param winnerPlayer
     * @param humanMove
     * @param loserPlayer
     * @param botMove
     * @param type
     * @param roundNumber
     */
    public Result(IPlayer winnerPlayer, IPlayer loserPlayer, Move humanMove, Move botMove, ResultType type, int roundNumber) {
        this.winnerPlayer = winnerPlayer;
        this.loserPlayer = loserPlayer;
        this.humanMove = humanMove;
        this.botMove = botMove;
        this.type = type;
        this.roundNumber = roundNumber;
    }

    public Move getHumanMove() {
        return humanMove;
    }

    public IPlayer getWinnerPlayer() {
        return winnerPlayer;
    }

    public Move getBotMove() {
        return botMove;
    }

    public IPlayer getLoserPlayer() {
        return loserPlayer;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public ResultType getType() {
        return type;
    }
}
