package commontale;

import gui.Map;
import gui.MyOwnMenuBar;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logic.Game;
import logic.IGame;
import ui.TextInterface;


public class Main extends Application {

    private TextArea centralText;
    private IGame game;
    private Map map;
    private TextField addCommand;
    private MyOwnMenuBar menuBar;
    private Stage stage;

    public static void main(String[] args) {
        if(args.length == 0)
            launch(args);
        else{
            if(args[0].equals("-txt")){
                IGame game = new Game();
                TextInterface ui = new TextInterface(game);
                ui.play();
            }
            else{
                System.out.println("Not valid command");
                System.exit(1);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        setGame(new Game());
        map = new Map(game);
        menuBar = new MyOwnMenuBar(game,this);
        /*TextInterface uiGame = new TextInterface(game);
        uiGame.play();*/
        BorderPane borderPane = new BorderPane();

        centralText = new TextArea();
        getCentralText().setText(game.returnWelcome());
        getCentralText().setEditable(false);
        getCentralText().setWrapText(true);
        borderPane.setCenter(getCentralText());

        Label setCommand = new Label("Set command: ");
        setCommand.setFont(Font.font("Arial", FontWeight.BOLD,14));

        addCommand = new TextField("");
        addCommand.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String userCommand = addCommand.getText();
                String gameAnswer = game.compileCommand(userCommand);

                getCentralText().appendText("\n" + userCommand + "\n");
                getCentralText().appendText("\n" + gameAnswer + "\n");

                map.update();

                addCommand.setText("");

                if(game.isOver()) {
                    addCommand.setEditable(false);
                    getCentralText().appendText("\n" + game.returnEpilogue());
                }

            }
        });

        FlowPane bottomPanel = new FlowPane();
        bottomPanel.setAlignment(Pos.CENTER);
        bottomPanel.getChildren().addAll(setCommand, addCommand);

        borderPane.setBottom(bottomPanel);
        borderPane.setRight(map);
        borderPane.setTop(menuBar);

        Scene scene = new Scene(borderPane,1400,600);

        primaryStage.setTitle("Welcome to the common world!");
        primaryStage.setScene(scene);
        primaryStage.show();
        addCommand.requestFocus();
    }

    public Map getMap(){ return map;}

    public TextArea getCentralText() {
        return centralText;
    }

    public void setGame(IGame game) {
        this.game = game;
    }

    public Stage getStage() {
        return stage;
    }
}
