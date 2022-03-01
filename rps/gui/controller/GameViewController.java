package rps.gui.controller;

// Java imports
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import rps.bll.game.GameManager;
import rps.bll.game.Move;
import rps.bll.game.Result;
import rps.bll.player.IPlayer;
import rps.bll.player.Player;
import rps.bll.player.PlayerType;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author smsj
 */
public class GameViewController implements Initializable {
    @FXML
    private Label txtHumanName;
    @FXML
    private Label txtBotName;
    @FXML
    private ImageView imgHumanPick;
    @FXML
    private ImageView imgBotPick;
    @FXML
    private TextArea resultField;

    private final static String BOT_NAME = "Pikachu";
    private final static String PLAYER_NAME = "Tim";
    private Image rockImage;
    private Image paperImage;
    private Image scissorsImage;

    private IPlayer human;
    private IPlayer bot;
    private GameManager gameManager;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        human = new Player(PLAYER_NAME, PlayerType.Human);
        bot = new Player(BOT_NAME, PlayerType.AI);
        gameManager = new GameManager(human, bot);

        txtBotName.setText(bot.getPlayerName());
        txtHumanName.setText(human.getPlayerName());
        resultField.setText("");

        rockImage = new Image("rps/gui/images/Rock.jpg");
        paperImage = new Image("rps/gui/images/Paper.jpg");
        scissorsImage = new Image("rps/gui/images/Scissors.png");
    }

    public void pickRock(MouseEvent mouseEvent) {
        playRound(Move.Rock);
    }

    public void pickPaper(MouseEvent mouseEvent) {
        playRound(Move.Paper);
    }

    public void pickScissor(MouseEvent mouseEvent) {
        playRound(Move.Scissor);
    }

    private void playRound(Move playerMove){
        Result result = gameManager.playRound(playerMove);
        if(result.getWinnerPlayer().getPlayerType() == PlayerType.Human){
            imgHumanPick.setImage(setPick(result.getWinnerMove()));
            imgBotPick.setImage(setPick(result.getLoserMove()));
        }
        else{
            imgBotPick.setImage(setPick(result.getWinnerMove()));
            imgHumanPick.setImage(setPick(result.getLoserMove()));
        }
    }

    private Image setPick(Move move){
        if (move == Move.Rock)
            return rockImage;

        else if (move == Move.Paper)
            return paperImage;

        else if (move == Move.Scissor)
            return scissorsImage;

        else
            return null;
    }
}
