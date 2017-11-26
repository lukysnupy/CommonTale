package gui;

import commontale.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import logic.IGame;
import util.Observer;

public class Room extends AnchorPane implements Observer{

    public IGame game;
    private ImageView player;

    public Room(IGame game){
        this.game = game;
        game.getGamePlan().registerObserver(this);
        init();
    }

    private void init(){
        ImageView imageView = new ImageView(new Image(Main.class.getResourceAsStream("/sources/room_preview.png"),
                300,300,false,true));

        player = new ImageView(new Image(Main.class.getResourceAsStream("/sources/player.png"),
                50,50,false,true));

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
        double[] playerPos = getPlayerPosition();
        setTopAnchor(player, playerPos[0]);
        setLeftAnchor(player, playerPos[1]);
    }

    private double[] getPlayerPosition(){
        switch (game.getGamePlan().getCurrentLocation().getDirection(game.getGamePlan().getPreviousLocation())){
            case "E":
                return new double[]{125,245};
            case "S":
                return new double[]{245,125};
            case "W":
                return new double[]{125,5};
            case "N":
            default:
                return new double[]{5,125};
        }
    }
}
