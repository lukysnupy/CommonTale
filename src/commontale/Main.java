package commontale;

import gui.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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
    private BagContent bagContent;
    private Exits exits;
    private GoComboBox goComboBox;
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

        centralText = new TextArea();
        getCentralText().setText(game.returnWelcome());
        getCentralText().setEditable(false);
        getCentralText().setWrapText(true);

        map = new Map(game);
        room = new Room(game,centralText);
        exits = new Exits(game);
        bagContent = new BagContent(game.getBag());
        goComboBox = new GoComboBox(game);
        menuBar = new MyOwnMenuBar(game,this);

        Label setCommand = new Label("Set command: ");
        setCommand.setFont(Font.font("Arial", FontWeight.BOLD,14));

        addCommand = new TextField("ask grandma");
        addCommand.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String userCommand = addCommand.getText();
                String gameAnswer = game.compileCommand(userCommand);

                getCentralText().appendText("\n" + userCommand + "\n");
                getCentralText().appendText("\n" + gameAnswer + "\n");

                game.getGamePlan().notifyObservers();

                addCommand.setText("");

                if(game.isOver()) {
                    addCommand.setEditable(false);
                    getCentralText().appendText("\n" + game.returnEpilogue());
                }

                if(game.getTalking()){
                    goComboBox.setEmptyList();
                }
                else{
                    goComboBox.update();
                }
            }
        });

        FlowPane bottomPanel = new FlowPane();
        bottomPanel.setAlignment(Pos.CENTER);
        bottomPanel.getChildren().addAll(setCommand, addCommand);

        VBox leftPanel = new VBox();
        leftPanel.setAlignment(Pos.CENTER);

        Label mapLabel = new Label("Map: ");
        mapLabel.setFont(Font.font("Sans", FontWeight.BOLD,14));
        mapLabel.setPadding(new Insets(2,2,2,5));
        map.setPadding(new Insets(0,0,15,0));

        Label exitsLabel = new Label("Exits: ");
        exitsLabel.setFont(Font.font("Sans", FontWeight.BOLD,14));
        exitsLabel.setPadding(new Insets(20,2,2,5));
        exits.setPrefHeight(115);
        exits.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2){
                    String userCommand = "go " + goComboBox.getFullDirectionName(exits.getSelectionModel()
                            .getSelectedItem().toString().substring(0,1));
                    String gameAnswer = game.compileCommand(userCommand);

                    getCentralText().appendText("\n" + userCommand + "\n");
                    getCentralText().appendText("\n" + gameAnswer + "\n");

                    game.getGamePlan().notifyObservers();
                }
            }
        });

        HBox goBox = new HBox();
        goBox.setPadding(new Insets(15,5,5,5));
        goBox.setAlignment(Pos.CENTER);

        Label setGo = new Label("Set direction: ");
        setGo.setFont(Font.font("Arial", FontWeight.BOLD,14));
        setGo.setAlignment(Pos.CENTER);

        goComboBox.setValue("choose direction..");
        Button goButt = new Button();
        goButt.setText("Go");
        goButt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String dir = goComboBox.getValue().toString();
                String userCommand = "go " + dir;
                String gameAnswer = game.compileCommand(userCommand);

                getCentralText().appendText("\n" + userCommand + "\n");
                getCentralText().appendText("\n" + gameAnswer + "\n");

                game.getGamePlan().notifyObservers();
            }
        });

        goBox.getChildren().addAll(setGo, goComboBox, goButt);

        leftPanel.getChildren().addAll(mapLabel, map, exitsLabel, exits, goBox);


        VBox rightPanel = new VBox();
        rightPanel.setAlignment(Pos.CENTER);

        Label bagLabel = new Label("Bag: ");
        bagLabel.setFont(Font.font("Sans", FontWeight.BOLD,14));
        bagLabel.setPadding(new Insets(2,2,2,5));
        bagContent.setPrefHeight(68);
        bagContent.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2){
                    String userCommand = "throw " + bagContent.getSelectionModel().getSelectedItem().toString();
                    String gameAnswer = game.compileCommand(userCommand);

                    getCentralText().appendText("\n" + userCommand + "\n");
                    getCentralText().appendText("\n" + gameAnswer + "\n");

                    game.getGamePlan().notifyObservers();
                }
            }
        });

        Label roomLabel = new Label("Room: ");
        roomLabel.setFont(Font.font("Sans", FontWeight.BOLD,14));
        roomLabel.setPadding(new Insets(20,2,2,5));

        rightPanel.getChildren().addAll(bagLabel, bagContent, roomLabel, room);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(getCentralText());
        borderPane.setBottom(bottomPanel);
        borderPane.setLeft(leftPanel);
        borderPane.setRight(rightPanel);
        borderPane.setTop(menuBar);

        Scene scene = new Scene(borderPane,1400,720);

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

    public BagContent getBagContent(){
        return bagContent;
    }

    public Exits getExits(){
        return exits;
    }

    public GoComboBox getGoComboBox(){
        return goComboBox;
    }
}
