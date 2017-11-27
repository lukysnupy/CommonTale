package gui;

import commontale.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import logic.Bag;
import util.Observer;
import javafx.scene.control.*;

import java.util.Set;

public class BagContent extends ListView implements Observer{

    private Bag subject;
    private ObservableList<String> bagData;

    public BagContent(Bag subject){
        this.subject = subject;
        subject.registerObserver(this);
        init();
    }

    @Override
    public void update() {
        bagData.remove(0,bagData.size());
        Set<String> bagCont = subject.getBagContent();
        for (String item : bagCont) {
            if(item.equals("key1"))
                item = "key";
            bagData.add(item);
        }
    }

    private void init(){
        bagData = FXCollections.observableArrayList();
        setItems(bagData);
        setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                return new XCell();
            }
        });
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
                text.setText(item);
                icon.setImage(new Image(Main.class.getResourceAsStream("/sources/"+item+".png"),
                        25,25,false,true));
                setGraphic(hBox);
            }
        }
    }
}
