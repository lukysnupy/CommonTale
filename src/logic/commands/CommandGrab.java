/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.commands;

import logic.Bag;
import logic.Game;
import logic.GamePlan;

/**
 * Třída CommandGrab představuje instanci příkazu grab, pomocí kterého lze vzít
 * předměty a umístit je do batohu.
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public class CommandGrab implements ICommand{
    private static final String NAME = "grab";
    private Bag bag;
    private GamePlan gamePlan;
    private Game game;
    
    public CommandGrab(GamePlan gamePlan,Game game, Bag bag){
        this.gamePlan = gamePlan;
        this.game = game;
        this.bag = bag;
    }
    
    @Override
    public String execute(String... parametres) {
        if (parametres.length <= 0){
            return "I do not know, what do you want to grab";
        }
        String item = "";
        for (int i = 0; i<parametres.length; i++) {
            item += parametres[i];
            if(i != (parametres.length - 1)){
                item += " ";
            }
        }
        
        //Omezení pro první "level" hry
        if(game.getLevel() == 1){
            if(item.equals("wood") && 
               gamePlan.getCurrentLocation().getName().equals("forest")){
                return bag.addItem(gamePlan.getUnpositionedItem("wood"));
            }
            else if (bag.getLocked()){
                return "You don't have a bag!";
            }
            else{
                return "You have only wood-bag! You can only grab wood!";
            }
        }
              
        //Omezení pro předmět "weed"
        if(item.equals("weed") && 
                gamePlan.getCurrentLocation().getName().equals("secret place")){
            if(bag.getItem("scissors") != null){
                game.setLevel(6);
                bag.notifyObservers();
                return bag.addItem(gamePlan.getCurrentLocation().
                        grabItem("weed"));
            }
            else{
                return "You need to have scissors to get weed!";
            }
        }
        
        //Omezení pro "chest" (truhla)
        if(item.equals("chest")){
            return "Come on, you think that can carry this chest? You should "
                    + "better use command \"open chest\"";
        }
        
        //Zde již normální průběh
        
        if(!gamePlan.getCurrentLocation().hasItem(item)){
            return "There's no item like that!";
        }
        
        if(item.equals("sword")){
            game.setLevel(8);
        }

        return bag.addItem(item);
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
