package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import logic.IGame;
import util.Observer;

public class GoComboBox extends ComboBox implements Observer{

    private IGame game;
    private ObservableList<String> data;

    public GoComboBox(IGame game){
        this.game = game;
        game.getGamePlan().registerObserver(this);
        init();
    }

    @Override
    public void update() {
        data.remove(0,data.size());
        for(String dir : game.getGamePlan().getCurrentLocation().getWaysOut().keySet())
            data.add(getFullDirectionName(dir));
    }

    public String getFullDirectionName(String dir) {
        switch(dir){
            case "N":
                return "north";
            case "E":
                return "east";
            case "W":
                return "west";
            case "S":
            default:
                return "south";
        }
    }

    private void init(){
        data = FXCollections.observableArrayList();
        setItems(data);
        update();
    }

    void newGame(IGame newGame){
        game.getGamePlan().removeObserver(this);
        game = newGame;
        game.getGamePlan().registerObserver(this);
        update();
    }

    public void setEmptyList(){
        data.remove(0,data.size());
    }
}
