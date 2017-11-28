package gui;

import commontale.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import logic.IGame;
import util.Observer;

/**
 * Třída Map představuje GUI prvek vykreslující herní mapu a pozici hráče na mapě
 *
 * @author  Lukas Ruzicka
 * @version ZS 2017/2018
 */
public class Map extends AnchorPane implements Observer{

    public IGame game;
    private ImageView player;
    private ImageView bg;

    /**
     * Konstruktor třídy Map
     * @param game hra
     */
    public Map(IGame game){
        this.game = game;
        game.getGamePlan().registerObserver(this);
        init();
    }

    /**
     * Připraví prvek pro použití
     */
    private void init(){
        bg = new ImageView(new Image(Main.class.getResourceAsStream("/sources/map.png"),
                400,400,false,true));

        player = new ImageView(new Image(Main.class.getResourceAsStream("/sources/player_small.png"),
                25,25,false,true));

        this.getChildren().addAll(bg,player);

        update();
    }

    /**
     * Voláno při vytvoření nové hry
     * @param newGame hra
     */
    public void newGame(IGame newGame){
        game.getGamePlan().removeObserver(this);
        game = newGame;
        game.getGamePlan().registerObserver(this);
        update();
    }

    /**
     * Tato metoda je volána při změně subjectu
     */
    @Override
    public void update() {
        setTopAnchor(player, game.getGamePlan().getCurrentLocation().getPosTop());
        setLeftAnchor(player, game.getGamePlan().getCurrentLocation().getPosLeft());

        if(game.getLevel() == 5)
            bg.setImage(new Image(Main.class.getResourceAsStream("/sources/" +
                    game.getGamePlan().getSecretPlaceDirection() + ".png"),400,400,
                    false,true));
    }
}
