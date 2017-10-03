/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.commands;

import logic.Game;
import logic.GamePlan;

/**
 * Třída CommandHelp představuje instanci příkazu help, který zobrazí hráči
 * informace o základní funkcionalitě hry.
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public class CommandHelp implements ICommand {
    private final Game game;
    private final GamePlan gamePlan;
    private static final String NAZEV = "help";
    
    public CommandHelp(Game game, GamePlan gamePlan){
        this.game = game;
        this.gamePlan = gamePlan;
    }
    
    @Override
    public String execute(String... parametres) {
        return "This is Common Tale! Your quest is to kill dragon and save"
                + " beautiful princess.\n"
                + "If you want to know what to do next, try \"tip\" command\n"
                + "If you want to know available moves, try \"moves\" command\n"
                + "If you don't know, how to speak with characters:\n"
                + "For advice, use \"advice\" or \"advise me\"\n"
                + "For item, use \"item\" or \"give me item\"\n"
                + "For exchange item, use \"use\" + what to give to exchange\n"
                + gamePlan.getCurrentLocation().message();
    }

    @Override
    public String answer(String answer) {
        game.setTalking(false);
        return "Can't answer anything!";
    }

    @Override
    public String getName() {
        return NAZEV;
    }
    
}
