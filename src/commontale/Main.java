package commontale;

import gui.Map;
import gui.MyOwnMenuBar;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
    private TextField addGo;
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
        BorderPane borderPane = new BorderPane();

        centralText = new TextArea();
        getCentralText().setText(game.returnWelcome());
        getCentralText().setEditable(false);
        getCentralText().setWrapText(true);
        borderPane.setCenter(getCentralText());

        Label setGo = new Label("Set where you want to go: ");
        setGo.setFont(Font.font("Arial", FontWeight.BOLD,14));

        addGo = new TextField("");
        addGo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String userCommand = addGo.getText();
                if(userCommand.split("[ \t]+")[0].equals("go")){
                    String gameAnswer = game.compileCommand(userCommand);

                    getCentralText().appendText("\n" + userCommand + "\n");
                    getCentralText().appendText("\n" + gameAnswer + "\n");

                    map.update();
                }
                else{
                    getCentralText().appendText("\n\nIf you want to do something other from going somewhere just use " +
                            "the other window for that..\n\n");
                    addCommand.requestFocus();
                }

                addGo.setText("");
            }
        });

        Label setCommand = new Label("Set command: ");
        setCommand.setFont(Font.font("Arial", FontWeight.BOLD,14));
        setCommand.setPadding(new Insets(0,0,0,70));

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
                    addGo.setEditable(false);
                    getCentralText().appendText("\n" + game.returnEpilogue());
                }

                if(game.getTalking()){
                    addGo.setEditable(false);
                    addGo.setText("SPEAKING..");
                    addCommand.requestFocus();
                }
                else{
                    addGo.setText("");
                    addGo.setEditable(true);
                    if(userCommand.split("[ \t]+")[0].equals("go"))
                        addGo.requestFocus();
                    else
                        addCommand.requestFocus();
                }
            }
        });

        FlowPane bottomPanel = new FlowPane();
        bottomPanel.setAlignment(Pos.CENTER);
        bottomPanel.getChildren().addAll(setGo, addGo, setCommand, addCommand);

        borderPane.setBottom(bottomPanel);
        borderPane.setRight(map);
        borderPane.setTop(menuBar);

        Scene scene = new Scene(borderPane,1400,600);

        primaryStage.setTitle("Welcome to the common world!");
        primaryStage.setScene(scene);
        primaryStage.show();
        addGo.requestFocus();
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
