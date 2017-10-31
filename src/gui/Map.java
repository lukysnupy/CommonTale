package gui;

import commontale.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import logic.IGame;
import util.Observer;

public class Map extends AnchorPane implements Observer{

    public IGame game;
    private Circle point;

    public Map(IGame game){
        this.game = game;
        game.getGamePlan().registerObserver(this);
        init();
    }

    private void init(){
        ImageView imageView = new ImageView(new Image(Main.class.getResourceAsStream("/sources/map.png"),
                200,200,false,true));

        point = new Circle(10, Paint.valueOf("red"));

        this.getChildren().addAll(imageView,point);

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
        this.setTopAnchor(point, game.getGamePlan().getCurrentLocation().getPosTop());
        this.setLeftAnchor(point, game.getGamePlan().getCurrentLocation().getPosLeft());
    }
}
