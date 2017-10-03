/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.commands;

import logic.Game;

/**
 *
 * @author El Snupator
 */
public class CommandTip implements ICommand {
    private final Game game;
    private static final String NAZEV = "tip";
    
    public CommandTip(Game game){
        this.game = game;
    }
    
    @Override
    public String execute(String... parametres) {
        switch (game.getLevel()){
            case 1:
                return "You need to get a bag. If you didn't asnwer grandma's "
                        + "puzzle right, you need to go to the forest and bring"
                        + " her some wood";
            case 2:
                return "Go and talk to the old fisherman";
            case 3:
                return "Find out where the secret place is. Try to ask other "
                        + "characters for advice";
            case 4:
                return "Now you know, where the secret place is. Go there.";
            case 5:
                return "You need to get a joint for the imp. You can use "
                        + "scissors to get the weed. Or you can bring sandwich "
                        + "to the pothead. Meat can be gained by buying it in "
                        + "the store, or catching a squirrel (use command \""
                        + "catch squirrel\")";
            case 6:
                return "Now you have a joint, bring it to the imp.";
            case 7:
                return "Now you have a oxygen bomb. Go and fish out the sword!";
            case 8:
                return "Go to the cave and kill the dragon!";
            default:
                return "Oh, sorry, something is broken.. :(";
        }
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
