/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.commands;

import java.util.Random;
import logic.Bag;
import logic.GamePlan;
import logic.Character;
import logic.Game;

/**
 * Třída CommandAsk představuje instanci příkazu ask, pomocí kterého může hráč
 * mluvit s postavami ve hře.
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public class CommandAsk implements ICommand{
    private static final String NAME = "ask";
    private GamePlan gamePlan;
    private Game game;
    private Bag bag;
    private Character currentChar;
    private boolean failedPuzzle = false;
    
    
    public CommandAsk(GamePlan gamePlan, Game game, Bag bag){
        this.gamePlan = gamePlan;
        this.game = game;
        this.bag = bag;
    }
    
    @Override
    public String execute(String... parametres) {
        if (parametres.length == 0){
            return "Who do you want to ask?";
        }
        
        String character = ""; 
        for (String parametre: parametres) {
            character += parametre;
        }
        
        Character currentCharacter = gamePlan.getCurrentLocation().
                returnCharacter();
        
        if(!currentCharacter.getName().equals(character)){
            return "There's no person like that..";
        }
        else{
        //Omezení pro první "level" hry
        if(game.getLevel() == 1){
            if(character.equals("grandma")){
                this.currentChar = currentCharacter;
                if(!failedPuzzle){
                    Random rand = new Random();
                    int puzzleNumber = rand.nextInt(5);
                    game.setTalking(true);
                    return "\"Hey grandson! If you want to go to the world and "
                            + "save princess, you need to get a bag first. I'll"
                            + " tell you a puzzle and if you answer me right, "
                            + "I'll give you a bag. \n"
                            + "Soo.. " + 
                            currentCharacter.returnPuzzle(puzzleNumber) + "\"";
                }
                else if(bag.getItem("wood") != null){
                    game.increaseLevel();
                    return "\"Great! Thank you for wood and here's your bag. "
                            + "Don't forget, that only two things at once can "
                            + "fit in that bag. Good luck! And be careful of "
                            + "the homeless man in forests! \n"
                            + currentCharacter.returnAdvice() + "\"";
                }
                else{
                    return "Go to the forest and bring me some wood..";
                }
            }
            else{
                return "Go and talk to you grandma!";
            }
        }
        
        //Omezení pro draka
        if(currentCharacter.getName().equals("dragon")){
            return "Are you crazy man?? You can't ask dragon.. It's dragon!";
        }
        
        //Zde již normální průběh
            game.setTalking(true);
            this.currentChar = currentCharacter;
            return "\"" + currentChar.returnFirstSpeech() + "\"";
        }
    }

    @Override
    public String answer(String answer) {
        answer = answer.toLowerCase();
        
        //Omezení pro první "level" hry
        if(game.getLevel() == 1){
            bag.unlockBag();
            if(currentChar.checkAnswerForPuzzle(answer)){
                game.increaseLevel();
                game.setTalking(false);
                return "\"" + "Woah! I always knew you're smart! \n"
                        + "Here's your bag. Don't forget, that only two things "
                        + "at once can fit in that bag. Good luck and have fun!"
                        + " And be careful of the homeless man in forests! \n"
                        + currentChar.returnAdvice() + "\"";
            }
            else{
                failedPuzzle = true;
                game.setTalking(false);
                return "\"" +"Ohh, that's wrong. I thought you are smarter, you"
                        + " disappoint me. I can't give you the bag this easy, "
                        + "so go to the forest and bring me some wood! I'll "
                        + "give you axe and you get your wood-bag. After you'll"
                        + " bring me the wood, I'll give you the normal bag."
                        + "\"";
            }
        }
        
        //Omezení pro bezdomovce
        if(currentChar.getName().equals("homeless")){
            if(bag.getItem("rum") != null){
                game.setTalking(false);
                return "You give a rum to homeless. He got drunk and totter "
                        + "away \n";
            }
            else{
                game.setTalking(false);
                bag.removeItems();
                return "You have been robbed by homeless! \n" 
                        + gamePlan.getCurrentLocation().message();
            }
        }
        
        //Zde již normálbí průběh
        
        String[] words = answer.split("[ \t]+");
        
        if(words[0].equals("use")){
            String item = "";
            for (int i = 1; i<words.length; i++) {
            item += words[i];
            if(i != (words.length - 1)){
                item += " ";
            }
        }
            if(!currentChar.getWantItem()){
                return "\"" + currentChar.returnDontWantItem() + "\"";
            }
            if(currentChar.neededItem(item)){
                if(bag.getItem(item) != null && bag.bagContentCount() < 2){
                    String message = "";
                    message += "\"" + currentChar.obtainItem(item) +"\""+ "\n" +
                            bag.addItem(gamePlan.getUnpositionedItem(
                            currentChar.returnExchangedItem()));
                    game.setTalking(false);
                    if(currentChar.returnExchangedItem().equals("weed")){
                        game.setLevel(6);
                    }
                    if(currentChar.returnExchangedItem().equals("oxygen")){
                        game.setLevel(7);
                    }
                    return message;
                    }
                else if(bag.bagContentCount() >= 2){
                    game.setTalking(false);
                    return "You don't have enough space in bag, throw something"
                            + " away first";
                }
                else{
                    game.setTalking(false);
                    return "You don't have a thing like that in your bag";
                }
                }
            else{
                game.setTalking(false);
                return "\"" + currentChar.obtainItem(item) + "\"";
            }
            }
        
        switch(answer){
            case "advise me":
            case "advice":
            case "help me":
            case "ask for advice":
            case "give me advice":
            case "give advice":
                String advice = "\"" + currentChar.returnAdvice() + "\"";
                if(game.getLevel() == 2 && currentChar.getName().
                        equals("fisherman")){
                    game.increaseLevel();
                }
                if(game.getLevel() == 3 && currentChar.equals(gamePlan.
                        returnCharSecretPlace())){
                    game.increaseLevel();
                }
                game.setTalking(false);
                return advice;
            case "give me item":
            case "do you have item?":
            case "item":
            case "item please":
            case "i want item":
                String message = "\"" + currentChar.returnItemSpeech() + "\"";                
                if(currentChar.getCanGiveItem()){
                    if(bag.bagContentCount() >= 2){
                    return "You bag is full, throw something away and than ask "
                            + "me";
                    }
                    message += "\n" + bag.addItem(currentChar.giveItem());
                }
                game.setTalking(false);
                return message;
            case "end":
            case "fuck you":
            case "go away":
            case "let me alone":
            case "bye":
                game.setTalking(false);
                return "\"" + currentChar.returnGoodBye() + "\"" + "\n" +
                       gamePlan.getCurrentLocation().message();
            default:
                return "What did you just said?";
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
    
}
