/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.commands;

import logic.Bag;
import logic.Game;

/**
 * Třída CommandThrow představuje instance příkazu throw, který zahodí předmět z
 * batohu a umístí ho do aktuální lokace.
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public class CommandThrow implements ICommand{
    private static final String NAME = "throw";    
    private Bag bag;
    private Game game;
    
    public CommandThrow(Game game, Bag bag){
        this.game = game;
        this.bag = bag;
    }
    
    @Override
    public String execute(String... parametres) {
        if (parametres.length <= 0){
            return "I do not know, what do you want to throw";
        }
        String item = "";
        for (int i = 0; i<parametres.length; i++) {
            item += parametres[i];
            if(i != (parametres.length - 1)){
                item += " ";
            }
        }        
        return bag.throwItem(item);
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
