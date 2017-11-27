package gui;

import commontale.Main;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import logic.Character;
import logic.IGame;
import util.Observer;

import java.util.*;
import java.util.Map;

public class Room extends AnchorPane implements Observer{

    public IGame game;
    private ImageView bg;
    private ImageView player;
    private List<double[]> itemsPositions = new ArrayList<>();
    private List<double[]> charactersPositions = new ArrayList<>();
    private Map<String,ImageView> placedItems = new HashMap<>();
    private Character characterHere;
    private TextArea centralText;

    public Room(IGame game, TextArea centralText){
        this.game = game;
        this.centralText = centralText;
        game.getGamePlan().registerObserver(this);
        init();
    }

    private void init(){
        ImageView bg = new ImageView(new Image(Main.class.getResourceAsStream("/sources/room_preview.png"),
                300,300,false,true));

        player = new ImageView(new Image(Main.class.getResourceAsStream("/sources/player.png"),
                50,50,false,true));

        this.getChildren().addAll(bg,player);

        itemsPositions.add(new double[]{100,100});
        itemsPositions.add(new double[]{110,150});
        itemsPositions.add(new double[]{175,160});
        itemsPositions.add(new double[]{140,120});
        itemsPositions.add(new double[]{165,130});

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
        for (String item : placedItems.keySet()) {
            this.getChildren().remove(placedItems.get(item));
        }
        placedItems.clear();

        double[] playerPos = getPlayerPosition();
        setTopAnchor(player, playerPos[0]);
        setLeftAnchor(player, playerPos[1]);

        Collections.shuffle(itemsPositions);
        Set<String> itemSet = game.getGamePlan().getCurrentLocation().getItemSet();
        int i = 0;
        for (String item : itemSet) {
            ImageView itemView;
            if(item.equals("chest")){
                itemView = new ImageView(new Image(Main.class.getResourceAsStream("/sources/"+item+".png"),
                        50,50,false,true));
                this.getChildren().add(itemView);
                placedItems.put(item,itemView);
                setTopAnchor(itemView, 40.0);
                setLeftAnchor(itemView, 30.0);
                itemView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        String userCommand = "open chest";
                        String gameAnswer = game.compileCommand(userCommand);

                        centralText.appendText("\n" + userCommand + "\n");
                        centralText.appendText("\n" + gameAnswer + "\n");

                        game.getGamePlan().notifyObservers();
                    }
                });
            }
            else{
                itemView = new ImageView(new Image(Main.class.getResourceAsStream("/sources/"+item+".png"),
                        25,25,false,true));
                this.getChildren().add(itemView);
                placedItems.put(item,itemView);
                setTopAnchor(itemView, itemsPositions.get(i)[0]);
                setLeftAnchor(itemView, itemsPositions.get(i)[1]);
                itemView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        String userCommand = "grab " + item;
                        String gameAnswer = game.compileCommand(userCommand);

                        centralText.appendText("\n" + userCommand + "\n");
                        centralText.appendText("\n" + gameAnswer + "\n");

                        game.getGamePlan().notifyObservers();
                    }
                });
                i++;
            }
        }
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
