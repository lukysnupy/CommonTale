/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.commands;

import java.util.ArrayList;
import java.util.Collection;
import logic.Bag;
import logic.Game;
import logic.GamePlan;
import logic.Item;

/**
 * Třída CommandOpen představuje instance příkazu open, pomocí kterého lze 
 * otevřít truhlu
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public class CommandOpen implements ICommand{
    private static final String NAME = "open";
    private GamePlan gamePlan;
    private Game game;
    private Bag bag;
    
    public CommandOpen(GamePlan gamePlan, Game game, Bag bag){
        this.gamePlan = gamePlan;
        this.game = game;
        this.bag = bag;
    }

    @Override
    public String execute(String... parametres) {
        String parametre = "";
        for (int i = 0; i < parametres.length; i++) {
            parametre += parametres[i];
            if(i != (parametres.length - 1)){
                parametre += " ";
            }
        }
        
        if(!(parametre.equals("chest"))){
            return "What to open?";
        }
        
        if(!gamePlan.getCurrentLocation().hasItem("chest")){
            return "There's no chest in there!";
        }
        
        if(bag.returnItem("key") == null && bag.returnItem("key1") == null){
            return "You have no key!";
        }   
        
        Item chest = gamePlan.getCurrentLocation().returnItem("chest");
        
        if(chest.chestOpened()){
            return "You have already opened this chest..";
        }
        
        //Zkontrolujeme první klíč
        if(bag.returnItem("key") != null){
            if(chest.openChest(bag.returnItem("key"))){
                bag.getItem("key");
                bag.notifyObservers();
                Collection<Item> chestContent = new ArrayList<>();
                chestContent = chest.returnChestContent();
                for(Item item: chestContent){
                    gamePlan.getCurrentLocation().addItem(item);
                }
                return chest.returnItemsInChest() + "\n" + 
                    gamePlan.getCurrentLocation().message();
            }
        }
        //A druhý 
        if(bag.returnItem("key1") != null){
            if(chest.openChest(bag.returnItem("key1"))){
                bag.getItem("key1");
                bag.notifyObservers();
                Collection<Item> chestContent = new ArrayList<>();
                chestContent = chest.returnChestContent();
                for(Item item: chestContent){
                    gamePlan.getCurrentLocation().addItem(item);
                }
                return chest.returnItemsInChest() + "\n" + 
                    gamePlan.getCurrentLocation().message();
            }
        }
                
        return "You don't have the right key";
            
    }

    @Override
    public String answer(String answer) {
        game.setTalking(false);
        return "Can't answer anything!";
    }

    @Override
    public String getName() {
       return NAME;
    }
    
    
}
