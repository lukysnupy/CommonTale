package gui;

import commontale.Main;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import logic.IGame;
import util.Observer;

import java.util.*;
import java.util.Map;

/**
 * Třída Room představuje GUI prvek vykreslující současnou místnost a hráče, předměty a postavy nacházející se v dané
 * místnosti.
 *
 * @author  Lukas Ruzicka
 * @version ZS 2017/2018
 */
public class Room extends AnchorPane implements Observer{

    public IGame game;
    private ImageView bg;
    private ImageView player;
    private List<double[]> itemsPositions = new ArrayList<>();
    private List<double[]> charactersPositions = new ArrayList<>();
    private Map<String,ImageView> placedItems = new HashMap<>();
    private ImageView characterHere;
    private TextArea centralText;

    /**
     * Konstruktor třídy Room
     * @param game hra
     */
    public Room(IGame game, TextArea centralText){
        this.game = game;
        this.centralText = centralText;
        game.getGamePlan().registerObserver(this);
        init();
    }

    /**
     * Připraví prvek pro použití
     */
    private void init(){
        bg = new ImageView();

        player = new ImageView(new Image(Main.class.getResourceAsStream("/sources/player.png"),
                50,50,false,true));

        this.getChildren().addAll(bg,player);

        itemsPositions.add(new double[]{85,245});
        itemsPositions.add(new double[]{75,105});
        itemsPositions.add(new double[]{160,70});
        itemsPositions.add(new double[]{215,95});
        itemsPositions.add(new double[]{155,195});

        charactersPositions.add(new double[]{70,170});
        charactersPositions.add(new double[]{210,30});
        charactersPositions.add(new double[]{200,180});

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
        //Remove all items and characters
        for (String item : placedItems.keySet()) {
            this.getChildren().remove(placedItems.get(item));
        }
        placedItems.clear();
        if(characterHere != null)
            this.getChildren().remove(characterHere);

        //Reposition player
        double[] playerPos = getPlayerPosition();
        setTopAnchor(player, playerPos[0]);
        setLeftAnchor(player, playerPos[1]);

        //Position items in current location
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

        //Add tree if level 1 and location is forest
        if(game.getGamePlan().getCurrentLocation().getName().equals("forest") && game.getLevel() == 1){
            ImageView itemView = new ImageView(new Image(Main.class.getResourceAsStream("/sources/tree.png"),
                    64,64,false,true));
            this.getChildren().add(itemView);
            placedItems.put("tree",itemView);
            setTopAnchor(itemView, 118.0);
            setLeftAnchor(itemView, 118.0);
            itemView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String userCommand = "grab wood";
                    String gameAnswer = game.compileCommand(userCommand);

                    centralText.appendText("\n" + userCommand + "\n");
                    centralText.appendText("\n" + gameAnswer + "\n");

                    game.getGamePlan().notifyObservers();
                }
            });
        }

        //Add character, if there is one
        if(game.getGamePlan().getCurrentLocation().getHasCharacter()){
            String name = game.getGamePlan().getCurrentLocation().getCharacterHere();
            ImageView character;
            if(name.equals("dragon")){
                character = new ImageView(new Image(Main.class.getResourceAsStream("/sources/dragon.png")));
                character.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        String userCommand = "kill dragon";
                        String gameAnswer = game.compileCommand(userCommand);

                        centralText.appendText("\n" + userCommand + "\n");
                        centralText.appendText("\n" + gameAnswer + "\n");

                        game.getGamePlan().notifyObservers();
                    }
                });
            }
            else{
                character = new ImageView(new Image(Main.class.getResourceAsStream("/sources/" + name
                        + ".png")));
                character.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        String userCommand = "ask " + name;
                        String gameAnswer = game.compileCommand(userCommand);

                        centralText.appendText("\n" + userCommand + "\n");
                        centralText.appendText("\n" + gameAnswer + "\n");

                        game.getGamePlan().notifyObservers();
                    }
                });
            }
            this.getChildren().add(character);
            characterHere = character;
            positionCharacter(name,character);
        }
        else
            characterHere = null;

        //Set room background
        bg.setImage(new Image(Main.class.getResourceAsStream("/sources/" + game.getGamePlan()
                .getCurrentLocation().getName() + ".png"),300,300,false,
                true));
    }

    /**
     * Vrací pozici postavy
     * @param name jméno postavy
     * @param character image view postavy
     */
    private void positionCharacter(String name, ImageView character) {
        switch(name){
            case "shopgirl":
                setTopAnchor(character, 5.0);
                setLeftAnchor(character,125.0);
                break;
            case "dragon":
                setTopAnchor(character, 110.0);
                setLeftAnchor(character, 20.0);
                break;
            default:
                Collections.shuffle(charactersPositions);
                setTopAnchor(character,charactersPositions.get(0)[0]);
                setLeftAnchor(character,charactersPositions.get(0)[1]);
                break;
        }
    }

    /**
     * Vrací pozici hráče
     * @return double[] pozice hráče
     */
    private double[] getPlayerPosition(){
        if(game.getGamePlan().getCurrentLocation().getName().equals("under bridge"))
            return new double[]{125,245};
        if(game.getGamePlan().getCurrentLocation().getName().equals("cottage"))
            return new double[]{245,125};
        switch (game.getGamePlan().getCurrentLocation().getDirection(game.getGamePlan().getPreviousLocation())){
            case "E":
                return new double[]{125,245};
            case "S":
                if(game.getGamePlan().getCurrentLocation().getName().equals("bridge"))
                    return new double[]{125,245};
                return new double[]{245,125};
            case "W":
                return new double[]{125,5};
            case "N":
            default:
                return new double[]{5,125};
        }
    }
}
