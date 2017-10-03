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
 * Třída CommandKill představuje instanci příkazu kill, pomocí kterého lze zabít
 * draka
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public class CommandKill implements ICommand{
    private static final String NAME = "kill";
    private Game game;
    private GamePlan gamePlan;
    private Bag bag;
    
    public CommandKill(Game game, GamePlan gamePlan, Bag bag){
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
        
        if(!parametre.equals("dragon")){
            return "Kill who?";
        }
        
        if(!bag.containsItem("sword")){
            return "You don't have a sword! How do you think you could kill a "
                    + "dragon? By hands?";
        }
        
        game.setIsOver(true);
        return "Congratulations, you did it! You killed the dragon! Wow! \n"
                + "The princess is saven and yours. The city celebrates you."
                + " You're the true hero!!";
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
