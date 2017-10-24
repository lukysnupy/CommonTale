package commontale;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Game;
import logic.IGame;
import ui.TextInterface;

import java.awt.*;


public class Main extends Application {

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

    ListView<String> colorsList;
    ObservableList<String> colorsData;

    @Override
    public void start(Stage primaryStage) {
        /*Button btn = new Button();
        btn.setText("Enter the common world..");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                IGame game = new Game();
                TextInterface ui = new TextInterface(game);
                ui.play();
            }
        });*/

        IGame game = new Game();
        /*TextInterface uiGame = new TextInterface(game);
        uiGame.play();*/

        BorderPane borderPane = new BorderPane();

        Text centralText = new Text();
        centralText.setText(game.returnWelcome());
        borderPane.setCenter(centralText);

        Label setCommand = new Label("Set command");
        setCommand.setFont(Font.font("Arial", FontWeight.BOLD,14));

        FlowPane bottomPanel = new FlowPane();
        bottomPanel.setAlignment(Pos.CENTER);
        bottomPanel.getChildren().add(setCommand);
        borderPane.setBottom(bottomPanel);

        Scene scene = new Scene(borderPane,1400,600);

        primaryStage.setTitle("Welcome to the common world!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
