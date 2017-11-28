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


/**
 * Třída BagContent představuje GUI prvek vypisující obsah batohu
 *
 * @author  Lukas Ruzicka
 * @version ZS 2017/2018
 */
public class BagContent extends ListView implements Observer{

    private Bag subject;
    private ObservableList<String> bagData;

    /**
     * Konstruktor třídy BagContent
     * @param subject batoh
     */
    public BagContent(Bag subject){
        this.subject = subject;
        subject.registerObserver(this);
        init();
    }

    /**
     * Tato metoda je volána při změně subjectu
     */
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

    /**
     * Připraví prvek pro použití
     */
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

    /**
     * Voláno při vytvoření nové hry
     * @param bag batoh
     */
    public void newGame(Bag bag){
        bag.removeObserver(this);
        subject = bag;
        bag.registerObserver(this);
        update();
    }

    /**
     * Třída pro vytvoření prvku ListView
     */
    static class XCell extends ListCell<String>{

        private HBox hBox = new HBox();
        private ImageView icon = new ImageView();
        private Label text = new Label("");

        /**
         * Konstruktor prvku ListView
         */
        public XCell(){
            text.setFont(Font.font("Sans", FontWeight.SEMI_BOLD,15));
            text.setAlignment(Pos.CENTER);
            text.setPadding(new Insets(0,0,0,10));
            hBox.getChildren().addAll(icon,text);
        }

        /**
         * Metoda aktualizuje prvek ListView
         * @param item předmět
         * @param empty prázdný
         */
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
