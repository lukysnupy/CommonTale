/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.commands;

import logic.Bag;
import logic.Game;
import logic.GamePlan;
import logic.Location;

/**
 * Třída CommandGo představuje instanci příkazu go, pomocí kterého se může hráč
 * přesouvat po mapě.
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public class CommandGo implements ICommand{
    private static final String NAME = "go";
    private GamePlan gamePlan;
    private Game game;
    private Bag bag;
    
    public CommandGo(Game game, GamePlan gamePlan, Bag bag){
        this.game = game;
        this.gamePlan = gamePlan;
        this.bag = bag;
    }

    @Override
    public String execute(String... parametres) {
        if (parametres.length == 0){
            return "I have no idea, where the hell do you want to go!";
        }
        
        String direction = ""; 
        for (String parametre : parametres) {
            direction += parametre;
        }
        Location current = gamePlan.getCurrentLocation();
        Location north = gamePlan.getCurrentLocation().getNextLocation("N");
        Location south = gamePlan.getCurrentLocation().getNextLocation("S");
        Location east = gamePlan.getCurrentLocation().getNextLocation("E");
        Location west = gamePlan.getCurrentLocation().getNextLocation("W");
                
        switch(direction){
            case "n":
            case "north":
                if (current.haveWay("N")){
                    if(north.getName().equals("sea") && 
                            !bag.containsItem("oxygen")){
                        return "You need to have oxygen bomb to enter the sea!";
                    }                        
                    gamePlan.setCurrentLocation(north);
                    gamePlan.updateItems();
                    gamePlan.updateCharacters();
                    if(north.getName().equals("secret place")){
                        game.setLevel(5);
                    }
                    return north.message();
                }
                else{
                    return "You can't go there! \n"
                            + gamePlan.getCurrentLocation().message();
                }
            case "s":
            case "south":
                if (current.haveWay("S")){
                    if(south.getName().equals("sea") && 
                            !bag.containsItem("oxygen")){
                        return "You need to have oxygen bomb to enter the sea!";
                    }
                    gamePlan.setCurrentLocation(south);
                    gamePlan.updateItems();
                    gamePlan.updateCharacters();
                    if(south.getName().equals("secret place")){
                        game.setLevel(5);
                    }
                    return south.message();
                }
                else{
                    return "You can't go there! \n"
                            + gamePlan.getCurrentLocation().message();
                }
            case "e":
            case "east":
                if (current.haveWay("E")){
                    if(east.getName().equals("sea") && 
                            !bag.containsItem("oxygen")){
                        return "You need to have oxygen bomb to enter the sea!";
                    }
                    gamePlan.setCurrentLocation(east);
                    gamePlan.updateItems();
                    gamePlan.updateCharacters();
                    if(east.getName().equals("secret place")){
                        game.setLevel(5);
                    }
                    return east.message();
                }
                else{
                    return "You can't go there! \n"
                            + gamePlan.getCurrentLocation().message();
                }
            case "w":
            case "west":
                if (current.haveWay("W")){
                    if(west.getName().equals("sea") && 
                            !bag.containsItem("oxygen")){
                        return "You need to have oxygen bomb to enter the sea!";
                    }
                    gamePlan.setCurrentLocation(west);
                    gamePlan.updateItems();
                    gamePlan.updateCharacters();
                    if(west.getName().equals("secret place")){
                        game.setLevel(5);
                    }
                    return west.message();
                }
                else{
                    return "You can't go there! \n"
                            + gamePlan.getCurrentLocation().message();
                }
            case "b":
            case "back":
                    gamePlan.setCurrentLocation(gamePlan.getPreviousLocation());
                    gamePlan.updateItems();
                    gamePlan.updateCharacters();
                    return gamePlan.getCurrentLocation().message();
            default:
                return "You need to insert direction (north, south, east, west)"
                        + " to get somewhere. Now you're staying where you have"
                        + " been \n" + current.message();
        }
        
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
