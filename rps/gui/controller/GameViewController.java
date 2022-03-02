package rps.gui.controller;

// Java imports
import javafx.animation.RotateTransition;
import javafx.animation.StrokeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
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
    public DialogPane dialogPane;
    public StackPane stackPane;
    @FXML
    private TextField nameInput;
    @FXML
    private Label txtHumanName;
    @FXML
    private Label txtBotName;
    @FXML
    private ImageView imgHumanPick;
    @FXML
    private ImageView imgBotPick;

    private final static String BOT_NAME = "Detective Pikachu";
    private Image rockImage;
    private Image paperImage;
    private Image scissorsImage;
    private Image cardBack;

    private IPlayer human;
    private IPlayer bot;
    private GameManager gameManager;


    @FXML
    private void newPlayer(){
        try{
            human.setPlayerName(nameInput.getText());
            txtHumanName.setText(human.getPlayerName());
            stackPane.getChildren().remove(dialogPane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        human = new Player("Player", PlayerType.Human);
        bot = new Player(BOT_NAME, PlayerType.AI);
        gameManager = new GameManager(human, bot);

        txtBotName.setText(bot.getPlayerName());

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

        playCardAnimation();

        imgHumanPick.setImage(setPick(result.getHumanMove()));
        imgBotPick.setImage(setPick(result.getBotMove()));
    }

    private void playCardAnimation(){
        imgHumanPick.setImage(cardBack);
        imgBotPick.setImage(cardBack);
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.millis(200));
        rotateTransition.setNode(imgBotPick);
        rotateTransition.setByAngle(-10);
        rotateTransition.setCycleCount(6);
        rotateTransition.setAutoReverse(true);
        rotateTransition.play();

        RotateTransition transition = new RotateTransition();
        transition.setDuration(Duration.millis(200));
        transition.setNode(imgHumanPick);
        transition.setByAngle(10);
        transition.setCycleCount(6);
        transition.setAutoReverse(true);
        transition.play();
    }

    private void playHoverAnimation(ImageView imageView){
        

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
