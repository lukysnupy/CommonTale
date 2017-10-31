package gui;

import commontale.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
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
        Menu newFile = new Menu("Adventure");

        MenuItem newGame = new MenuItem("New Game");//, new ImageView(new Image(Main.class.getResourceAsStream(
                //"sources/icon.png"))));
        newGame.setAccelerator(KeyCombination.keyCombination("ctrl+G"));

        MenuItem exitGame = new MenuItem("Exit Game");
        exitGame.setAccelerator(KeyCombination.keyCombination("ctrl+E"));

        newFile.getItems().addAll(newGame,exitGame);

        this.getMenus().addAll(newFile);

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
                main.getCentralText().setText(game.returnWelcome());
            }
        });

    }
}
