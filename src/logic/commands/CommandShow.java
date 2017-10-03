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
 * Třída CommandShow představuje instanci příkazu show, který zobrazuje info o 
 * předmětech a obsahu batohu
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public class CommandShow implements ICommand{
    private static final String NAME = "show";
    private Bag bag;
    private GamePlan gamePlan;
    private Game game;
    
    public CommandShow(Bag bag, GamePlan gamePlan, Game game){
        this.bag = bag;
        this.gamePlan = gamePlan;
        this.game = game;
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
        
        if(parametre.equals("bag")){
            return bag.returnBagContent();
        }
        
        if(gamePlan.getCurrentLocation().hasItem(parametre)){
            return gamePlan.getCurrentLocation().returnItem(parametre).
                    getDescription();
        }
        
        if(bag.containsItem(parametre)){
            return bag.returnItem(parametre).getDescription();
        }
        
        return "There's no item like that around";
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
