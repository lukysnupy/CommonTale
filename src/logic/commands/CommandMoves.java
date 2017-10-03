/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.commands;

import logic.Game;

/**
 * Třída CommandMoves představuje instanci příkazu moves, který vypíše všechny
 * platné příkazu v daném stavu hry
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public class CommandMoves implements ICommand{
    private static final String NAME = "moves";
    private AvailableCommands commands;
    private Game game;
    
    public CommandMoves(AvailableCommands commands, Game game){
        this.commands = commands;
        this.game = game;
    }

    @Override
    public String execute(String... parametres) {
        if(parametres.length > 0){
            return "Come on, what else do you want?";
        }
        
        return commands.returnValidCommands();
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
