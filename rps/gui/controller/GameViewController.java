package rps.gui.controller;

// Java imports

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import rps.bll.game.GameManager;
import rps.bll.game.Move;
import rps.bll.game.Result;
import rps.bll.game.ResultType;
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
    private StackPane stackPane;
    @FXML
    private Label txtHumanWin;
    @FXML
    private Label txtTies;
    @FXML
    private Label txtBotWins;
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
    private Image pikaRock;
    private Image pikaPaper;
    private Image pikaScissors;
    private Image cardBack;

    private IPlayer human;
    private IPlayer bot;
    private GameManager gameManager;

    private int humanWins, botWins, ties;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        human = new Player("Player", PlayerType.Human);
        bot = new Player(BOT_NAME, PlayerType.AI);
        gameManager = new GameManager(human, bot);

        txtBotName.setText(bot.getPlayerName());

        rockImage = new Image("rps/gui/images/rocky small.png");
        paperImage = new Image("rps/gui/images/paper small.png");
        scissorsImage = new Image("rps/gui/images/scissors small.png");
        pikaPaper = new Image("rps/gui/images/pika paper.png");
        pikaRock = new Image("rps/gui/images/pika rock.png");
        pikaScissors = new Image("rps/gui/images/pika scissors.png");
        cardBack = new Image("rps/gui/images/back small.png");
        newPlayer();
    }

    private void newPlayer() {
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setStyle("-fx-background-image: url('https://images.unsplash.com/photo-1570475735025-6cd1cd5c779d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80'); -fx-alignment: CENTER");
        Label label = new Label("Please enter your name:");
        TextField textField = new TextField("Player");
        textField.setMaxWidth(200);
        Button button = new Button("OK");
        button.setOnAction(event -> {
            human.setPlayerName(textField.getText());
            txtHumanName.setText(textField.getText());
            stackPane.getChildren().remove(vBox);
        });
        vBox.getChildren().addAll(label, textField, button);
        stackPane.getChildren().add(vBox);
    }

    public void pickRock(MouseEvent mouseEvent) throws InterruptedException {
        playRound(Move.Rock);
    }

    public void pickPaper(MouseEvent mouseEvent) throws InterruptedException {
        playRound(Move.Paper);
    }

    public void pickScissor(MouseEvent mouseEvent) throws InterruptedException {
        playRound(Move.Scissor);
    }

    private void playRound(Move playerMove) throws InterruptedException {
        Result result = gameManager.playRound(playerMove);
        playCardAnimation();


        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setPick(result);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    updateScores(result);
                }
            });
        });
        thread.start();
    }

    private void updateScores(Result result) {
        if (result.getType() == ResultType.Tie){
            ties++;
            txtTies.setText(String.valueOf(ties));
        }
        else {
            if (result.getWinnerPlayer().getPlayerType() == PlayerType.Human){
                humanWins++;
                txtHumanWin.setText(String.valueOf(humanWins));
            }
            else {
                botWins++;
                txtBotWins.setText(String.valueOf(botWins));
            }
        }
    }

    private void playCardAnimation(){
        imgHumanPick.setImage(cardBack);
        imgBotPick.setImage(cardBack);
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.millis(250));
        rotateTransition.setNode(imgBotPick);
        rotateTransition.setByAngle(-10);
        rotateTransition.setCycleCount(6);
        rotateTransition.setAutoReverse(true);
        rotateTransition.play();

        RotateTransition transition = new RotateTransition();
        transition.setDuration(Duration.millis(250));
        transition.setNode(imgHumanPick);
        transition.setByAngle(10);
        transition.setCycleCount(6);
        transition.setAutoReverse(true);
        transition.play();
    }


    private void setPick(Result result){
        Move humanMove = result.getHumanMove();
        Move botMove = result.getBotMove();

        if(humanMove == Move.Rock)
            imgHumanPick.setImage(rockImage);
        else if(humanMove == Move.Paper)
            imgHumanPick.setImage(paperImage);
        else
            imgHumanPick.setImage(scissorsImage);

        if(botMove == Move.Rock)
            imgBotPick.setImage(pikaRock);
        else if (botMove == Move.Paper)
            imgBotPick.setImage(pikaPaper);
        else
            imgBotPick.setImage(pikaScissors);
    }
}
