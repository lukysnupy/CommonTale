package commontale;

import gui.BagContent;
import gui.Map;
import gui.MyOwnMenuBar;
import gui.Room;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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
    private Room room;
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
        room = new Room(game);
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
                    room.update();
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

        addCommand = new TextField("ask grandma");
        addCommand.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String userCommand = addCommand.getText();
                String gameAnswer = game.compileCommand(userCommand);

                getCentralText().appendText("\n" + userCommand + "\n");
                getCentralText().appendText("\n" + gameAnswer + "\n");

                map.update();
                room.update();

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

        VBox leftPanel = new VBox();
        leftPanel.setAlignment(Pos.CENTER);
        map.setPadding(new Insets(0,0,15,0));
        Label roomLabel = new Label("Room: ");
        setCommand.setFont(Font.font("Arial", FontWeight.BOLD,14));
        setCommand.setPadding(new Insets(2,2,2,5));
        HBox roomBox = new HBox();
        roomBox.setAlignment(Pos.CENTER);
        roomBox.getChildren().addAll(roomLabel, room);
        leftPanel.getChildren().addAll(map, roomBox);

        VBox rightPanel = new VBox();
        rightPanel.setAlignment(Pos.CENTER);
        Label bagLabel = new Label("Bag: ");
        bagLabel.setFont(Font.font("Arial", FontWeight.BOLD,14));
        bagLabel.setPadding(new Insets(2,2,2,5));
        BagContent bagContent = new BagContent(game.getBag());
        rightPanel.getChildren().addAll(bagLabel, bagContent);

        borderPane.setBottom(bottomPanel);
        borderPane.setLeft(leftPanel);
        borderPane.setRight(rightPanel);
        borderPane.setTop(menuBar);

        Scene scene = new Scene(borderPane,1400,780);

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

    public Room getRoom() {
        return room;
    }
}
