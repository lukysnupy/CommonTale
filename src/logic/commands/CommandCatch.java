/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.commands;

import java.util.Random;
import logic.Bag;
import logic.Game;
import logic.GamePlan;

/**
 * Třída CommandCatch představuje instanci příkazu catch, pomocí kterého může
 * hráč chytit veverku v lese
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public class CommandCatch implements ICommand{
    private static final String NAME = "catch";
    private GamePlan gamePlan;
    private Bag bag;
    private Game game;
    
    public CommandCatch(Game game, GamePlan gamePlan, Bag bag){
        this.game = game;
        this.gamePlan = gamePlan;
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
        
        if(!parametre.equals("squirrel")){
            return "What do you want to catch?";            
        }
        
        if(!gamePlan.getCurrentLocation().getName().equals("forest")){
            return "Your not in a forest.. Where the hell you think could be "
                    + "squirrel here?";
        }
        
        if(bag.returnItem("can") == null){
            return "You need to have a can, which you can throw on squirrel";
        }
        
        Random rand = new Random();
        boolean catched = rand.nextBoolean();
        if(catched){
            String message = "Good job, you hit it right in between its eyes!";
            bag.getItem("can");
            message += "\n" + bag.addItem(gamePlan.getUnpositionedItem("meat"));
            return message;
        }
        
        gamePlan.getCurrentLocation().addItem(bag.getItem("can"));
        bag.notifyObservers();
        return "Ohh, you've missed..";
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
