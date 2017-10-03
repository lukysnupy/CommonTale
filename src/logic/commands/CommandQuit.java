/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.commands;

import logic.Game;
import logic.GamePlan;

/**
 * Třída CommandQuit představuje instanci příkazu quit, který ukončuje hru
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public class CommandQuit implements ICommand {
    private static final String NAME = "quit";
    private final Game game;
    private final GamePlan gamePlan;
    private int attempts;
    
    public CommandQuit(Game game, GamePlan gamePlan){
        this.game = game;
        this.gamePlan = gamePlan;
    }
    
    @Override
    public String execute(String... parametres) {
        if (parametres.length > 0){
            return "What do you want to quit?";
        }
        game.setTalking(true);
        attempts = 0;
        return "Are you sure?";
    }

    @Override
    public String answer(String answer) {
        switch(answer){
            case "yes":
            case "yea":
            case "sure":
            case "yes please":
                game.setIsOver(true);
                return "Okey, as you wish..";
            case "no":
            case "no way":
            case "noo":
                game.setTalking(false);
                return gamePlan.getCurrentLocation().message();
            default:
                if (attempts > 2){
                    game.setTalking(false);
                    return "I see that you don't know how did you get here. "
                            + "So I get you back, where you've been.."
                            + "\n"
                            + gamePlan.getCurrentLocation().message();
                }
                else {
                    attempts++;
                    return "I don't know what have you just wrote"
                            + "\n"
                            + "Are you sure, you want to quit?";
                }
                    
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
    
}
