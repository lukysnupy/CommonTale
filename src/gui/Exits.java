package gui;

import commontale.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import logic.IGame;
import logic.Location;
import util.Observer;
import java.util.Map;

public class Exits extends ListView implements Observer{

    private IGame game;
    private ObservableList<String> exitsData;

    public Exits(IGame game){
        this.game = game;
        game.getGamePlan().registerObserver(this);
        init();
    }

    @Override
    public void update() {
        exitsData.remove(0,exitsData.size());
        if(!game.getTalking()){
            Map<String,Location> waysOut = game.getGamePlan().getCurrentLocation().getWaysOut();
            for (String direction : game.getGamePlan().getCurrentLocation().getWaysOut().keySet()) {
                if(game.getLevel() < 4 && waysOut.get(direction).getName().equals("secret place"))
                    continue;
                exitsData.add(direction + waysOut.get(direction).getName());
            }
        }
    }

    private void init(){
        exitsData = FXCollections.observableArrayList();
        setItems(exitsData);
        setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                return new XCell();
            }
        });
        update();
    }

    void newGame(IGame newGame){
        game.getGamePlan().removeObserver(this);
        game = newGame;
        game.getGamePlan().registerObserver(this);
        update();
    }

    static class XCell extends ListCell<String>{

        private HBox hBox = new HBox();
        private ImageView icon = new ImageView();
        private Label text = new Label("");

        public XCell(){
            text.setFont(Font.font("Sans", FontWeight.SEMI_BOLD,15));
            text.setAlignment(Pos.CENTER);
            text.setPadding(new Insets(0,0,0,10));
            hBox.getChildren().addAll(icon,text);
        }

        @Override
        protected void updateItem(String item, boolean empty){
            super.updateItem(item,empty);
            setText(null);
            if(empty)
                setGraphic(null);
            else{
                text.setText(item.substring(1));
                icon.setImage(new Image(Main.class.getResourceAsStream("/sources/"+item.substring(0,1)+".png"),
                        20,20,false,true));
                setGraphic(hBox);
            }
        }
    }
}
