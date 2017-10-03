package logic;

import logic.commands.ICommand;
import logic.commands.AvailableCommands;

/**
 * Třída Game představuje hlavní logiku hry. Vytváří instanci třídy GamePlan,
 * která inicializuje místnosti hry a vytváří seznam platných příkazů a instance
 * tříd provádějící jednotlivé příkazy.
 * Vypisuje uvítací a ukončovací text hry. 
 * Vyhodnocuje jednotlivé příkazy zadané uživatelem.
 * 
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public class Game implements IGame{
    
    private boolean isOver = false;
    private final GamePlan gamePlan;
    private final AvailableCommands commands;
    private final Bag bag;
    private boolean talking = false;
    private ICommand lastValidCommand;
    private int level;
    
    
    /**
     * Konstruktor vytvoří instanci třídy GamePlan (která vytvoří plán hry) a
     * instanci třídy AvailableCommands, která ukládá dostupné příkazy.
     */
    public Game(){
        gamePlan = new GamePlan(this);
        bag = new Bag(gamePlan);
        commands = new AvailableCommands(this,gamePlan,bag);
        level = 1;
    }
    
    /**
     * Vrátí úvodní zprávu pro hráče.
     *
     * @return    vrací se řetězec, který se má vypsat na obrazovku
     */
    @Override
    public String returnWelcome() {
        return "Welcome to the Common Tale! \n"
                + "You live like a common guy in your grandma's cottage and "
                + "one day a dragon came to your village. He kidnapped a "
                + "princess that was promised to be your wife and helds her in"
                + " a cave. \nYou need to kill the dragon and save your "
                + "princess! \n"
                + "Few tips: Other characters can give you advice about what "
                + "you need to do next. They can also have item, that they can "
                + "give you. \n"
                + "Be careful in forests. You may encounter a homeless man, who"
                + " can steal all things you have in your bag, unless you give "
                + "him a rum and get him drunk. \n"
                + "I think that's everything for now. First you need to ask "
                + "your grandma to start your great story. Wish you good luck! "
                + "\n"
                + gamePlan.getCurrentLocation().message();
    }
    
    /**
     * Vrátí závěrečnou zprávu pro hráče.
     *
     * @return    vrací se řetězec, který se má vypsat na obrazovku
     */
    @Override
    public String returnEpilogue() {
        return "Thanks for playing! Hope to see you again!";
    }
    
    /**
     * Vrací informaci o tom, zda hra již skončila bez ohledu na to, jestli
     * hráč vyhrál, prohrál nebo zadal příkaz quit
     *
     * @return    vrací true, pokud hra skončila
     */
    @Override
    public boolean isOver() {
        return isOver;
    }
    
    /**
     * Metoda zpracuje zadaný řetezec tak, že ho rozdělí na slova a zjistí 
     * jaký příkaz se má vykonat (první 1-2 slova). Pokud příkaz existuje pošle
     * mu parametry (další slova) a provede ho.
     *
     * @param     line text, který zadal uživatel jako příkaz do hry
     * @return    vrací se řetězec, který se má vypsat na obrazovku
     */
    @Override
    public String compileCommand(String line) {
        line = line.toLowerCase();
        String toBeTyped = "";
        if (!talking){
        String[] words = line.split("[ \t]+");
        String commandWord = words[0];
        int shift = 1;
        if (commandWord.equals("go") && words[1].equals("to")){
            shift = 2;
        }
        String[] parameters = new String[words.length-shift];
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = words[i+shift];
        }
        if(commands.isValidCommand(commandWord)){
            lastValidCommand = commands.getCommand(commandWord);
            toBeTyped = lastValidCommand.execute(parameters);
            commands.updateCommands();
            if(gamePlan.getCurrentLocation().getHasCharacter()){
                if(gamePlan.getCurrentLocation().returnCharacter()
                        .getActionable()){
                    lastValidCommand = commands.getCommand("ask");
                    toBeTyped = lastValidCommand.execute(gamePlan
                            .getCurrentLocation().returnCharacter().getName());
                }
                if(gamePlan.getCurrentLocation().getHasCharacter("shark")){
                    toBeTyped = "Ohh a shark!! Get away quickly! \n"
                            + "You runned back as quick as you can";
                    gamePlan.setCurrentLocation(gamePlan.getPreviousLocation());
                    toBeTyped += "\n" + gamePlan.getCurrentLocation().message();
                }
            }
        }
        else{
            toBeTyped = "This command is not valid! Try it again, or use "
                      + "command \"moves\"";
        }
        }
        else {
            toBeTyped = lastValidCommand.answer(line);
        }
        return toBeTyped;
    }
    
    /**
     * Metoda vrátí odkaz na herní plán, je využita hlavně v testech,
     * kde se jejím prostřednictvím získává aktualní místnost hry.
     *
     * @return    odkaz na herní plán
     */
    @Override
    public GamePlan getGamePlan() {
        return gamePlan;
    }
    
    /**
     * Nastaví, zda hra stále probíhá či nikoli, v závislosti na zadané hodnotě.
     * Využíváno zejména pro ukončení hry (příkazem quit nebo dohráním hry).
     * @param isOver true pro konec hry, false pro pokračování
     */
    public void setIsOver(boolean isOver){
        this.isOver = isOver;
    }
    
    /**
     * Nastaví, zda je hra právě v režimu rozhovoru (tzn. nezpracovávají se 
     * příkazy, ale odpovědi)
     * @param talking True pro režim rozhovoru, false pro normální průběh hry
     */
    public void setTalking(boolean talking){
        this.talking = talking;
    }
    
    /**
     * Metoda pro nastavení hodnoty posledního příkazu. Důlěžitá hlavně pro 
     * lokace, ve kterých jsou postavy, které začnou mluvit ihned po vstupu.
     * @param commandWord slovo příkazu
     */
    public void setLastValidCommand(String commandWord){
        lastValidCommand = commands.getCommand(commandWord);
    }
    
    /**
     * Vrací zpátky informaci o pokroku ve hře - level (hodnoty 1-8)
     * @return "level" hry značící pokrok v příběhu
     */
    public int getLevel(){
        return level;
    }
    
    /**
     * Zvýší "level" hry - volá se při pokroku v příběhu
     */
    public void increaseLevel(){
        level++;
    }
    
    /**
     * Nastaví "level" hry na zadaný
     * @param level hodnoty 1-8
     */    
    public void setLevel(int level){
        this.level = level;
    }
    
    /**
     * Vrací logickou hodnotu, zda je batoh hráče plný či nikoliv
     * @return true pokud je plný, false pokud ne
     */
    public boolean fullBag(){
        return bag.bagContentCount() == 2;
    }
}
