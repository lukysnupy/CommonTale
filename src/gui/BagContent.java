package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import logic.Bag;
import util.Observer;

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
            bagData.add(item);
        }
    }

    private void init(){
        bagData = FXCollections.observableArrayList();
        setItems(bagData);
        update();
    }
}
