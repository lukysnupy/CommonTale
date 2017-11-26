package gui;

import commontale.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import logic.Game;
import logic.IGame;

public class MyOwnMenuBar extends MenuBar {

    private IGame game;
    private Main main;

    public MyOwnMenuBar(IGame game, Main main){
        init();
        this.game = game;
        this.main = main;
    }

    private void init(){
        Menu newFile = new Menu("Common Tale");
        Menu help = new Menu("Help");

        MenuItem newGame = new MenuItem("New Game");//, new ImageView(new Image(Main.class.getResourceAsStream(
                //"sources/icon.png"))));
        newGame.setAccelerator(KeyCombination.keyCombination("ctrl+G"));

        MenuItem exitGame = new MenuItem("Exit Game");
        exitGame.setAccelerator(KeyCombination.keyCombination("ctrl+E"));

        newFile.getItems().addAll(newGame,exitGame);

        MenuItem about = new MenuItem("About");
        MenuItem helpItem = new MenuItem("Help (CZ)");
        helpItem.setAccelerator(KeyCombination.keyCombination("ctrl+H"));

        help.getItems().addAll(about,helpItem);

        this.getMenus().addAll(newFile,help);

        exitGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game = new Game();
                main.setGame(game);
                main.getMap().newGame(game);
                main.getRoom().newGame(game);
                main.getCentralText().setText(game.returnWelcome());
            }
        });

        about.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
                aboutAlert.setTitle("About");
                aboutAlert.setHeaderText("Common Tale");
                aboutAlert.setContentText("Tato hra je takovým obyčejným příběhem každodenního života. Do vesnice " +
                        "přiletěl drak a unesl princeznu, která byla přislíbena našemu hrdinovi. Ten má nyní za úkol " +
                        "zabít draka a osvobodit princeznu. Na cestě na něj čeká spoustu nástrah a úkol rozhodně " +
                        "není jednoduchý. Draka lze zabít jen pomocí meče, který už je po staletí ztracen. Ty ho " +
                        "musíš najít a pomocí něj zabít draka, zachránit celou vesnici a hlavně zachránit svojí " +
                        "princeznu.\n \n" +
                        "Přeji ti na tvé cestě hodně štěstí a doufám, že si svou mysticko-sci-fi-fantasy cestu " +
                        "užiješ! \n \n" + "©Lukáš Růžička, 2017");
                aboutAlert.initOwner(main.getStage());

                aboutAlert.showAndWait();
            }
        });

        helpItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Stage stage = new Stage();
                stage.setTitle("Help");

                WebView webView = new WebView();
                webView.getEngine().load(Main.class.getResource("/sources/help.html").toExternalForm());

                stage.setScene(new Scene(webView,900,500));
                stage.show();

            }
        });

    }
}
