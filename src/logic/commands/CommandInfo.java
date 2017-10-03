/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.commands;

import logic.Game;
import logic.GamePlan;

/**
 * Třída CommandInfo představuje instanci příkazu info, který vypíše informace o
 * lokaci, předmětech, postavách a možných cest pryč.
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public class CommandInfo implements ICommand{
    private static final String NAME = "info";
    private GamePlan gamePlan;
    private Game game;
    
    public CommandInfo(GamePlan gamePlan, Game game){
        this.gamePlan = gamePlan;
        this.game = game;
    }
    
    @Override
    public String execute(String... parametres) {
        return gamePlan.getCurrentLocation().message();
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
