package gui;

import commontale.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import logic.IGame;
import util.Observer;

public class Map extends AnchorPane implements Observer{

    public IGame game;
    private ImageView player;

    public Map(IGame game){
        this.game = game;
        game.getGamePlan().registerObserver(this);
        init();
    }

    private void init(){
        ImageView imageView = new ImageView(new Image(Main.class.getResourceAsStream("/sources/map.png"),
                400,400,false,true));

        player = new ImageView(new Image(Main.class.getResourceAsStream("/sources/player_small.png"),
                25,25,false,true));

        this.getChildren().addAll(imageView,player);

        update();
    }

    public void newGame(IGame newGame){
        game.getGamePlan().removeObserver(this);
        game = newGame;
        game.getGamePlan().registerObserver(this);
        update();
    }

    @Override
    public void update() {
        setTopAnchor(player, game.getGamePlan().getCurrentLocation().getPosTop());
        setLeftAnchor(player, game.getGamePlan().getCurrentLocation().getPosLeft());
    }
}
