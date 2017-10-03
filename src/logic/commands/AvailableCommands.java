package logic.commands;

import java.util.HashMap;
import java.util.Map;
import logic.Bag;
import logic.Game;
import logic.GamePlan;

/**
 * Třída Available Commands udržuje dostupné příkazy pro konkrétní situaci ve
 * hře.
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public class AvailableCommands {
    private Map<String, ICommand> availableComms;
    private final ICommand quit;
    private final ICommand help;
    private final ICommand tip;
    private final ICommand go;
    private final ICommand grab;
    private final ICommand info;
    private final ICommand throwComm;
    private final ICommand ask;
    private final ICommand open;
    private final ICommand catchComm;
    private final ICommand show;
    private final ICommand moves;
    private final ICommand kill;
    private Game game;
    private GamePlan gamePlan;
    private Bag bag;
    
    /**
     * Konstruktor založí mapu
     * @param game instance současné hry
     * @param gamePlan herní plán
     * @param bag batoh
     */
    public AvailableCommands(Game game, GamePlan gamePlan, Bag bag){
        availableComms = new HashMap<>();
        quit = new CommandQuit(game, gamePlan);
        help = new CommandHelp(game, gamePlan);
        tip = new CommandTip(game);
        go = new CommandGo(game, gamePlan, bag);
        grab = new CommandGrab(gamePlan, game, bag);
        info = new CommandInfo(gamePlan, game);
        throwComm = new CommandThrow(game, bag);
        ask = new CommandAsk(gamePlan, game, bag);
        open = new CommandOpen(gamePlan, game, bag);
        catchComm = new CommandCatch(game, gamePlan, bag);
        show = new CommandShow(bag, gamePlan, game);
        moves = new CommandMoves(this, game);
        kill = new CommandKill(game, gamePlan, bag);
        this.game = game;
        this.gamePlan = gamePlan;
        this.bag = bag;
        updateCommands();
    }
    
    /**
     * Přidá příkaz do seznamu povolených příkazů
     * @param command příkaz k přidání
     */
    public void addCommand(ICommand command){
        availableComms.put(command.getName(), command);
    }
    
    /**
     * Metoda vrací příkaz jako objekt. Paramatrem je název příkazu
     * @param command příkaz v textové podobě
     * @return příkaz (instance ICommand)
     */
    public ICommand getCommand(String command){
        if (availableComms.containsKey(command))
            return availableComms.get(command);
        else{
            return null;
        }
    }
    
    /**
     * Vrátí logickou hodnotu, zda je příkaz možné použít, tzn. je v seznamu
     * povolených příkazů.
     * @param command příkaz v textové podobě
     * @return true pokud je příkaz valid, false pokud není
     */
    public boolean isValidCommand(String command){
        return availableComms.containsKey(command);
    }
    
    /**
     * Vymaže všechny příkazy a přidá všechny povolené a možné použít v dané
     * situaci ve hře.
     */
    public final void updateCommands(){
        availableComms.clear();
        addCommand(go);
        addCommand(grab);
        if(bag.bagContentCount() != 0){
        addCommand(throwComm);
        }
        if(gamePlan.getCurrentLocation().getHasCharacter()){
            addCommand(ask);
        }
        if(gamePlan.getCurrentLocation().hasItem("chest")){
            addCommand(open);
        }
        if(gamePlan.getCurrentLocation().getName().equals("forest")){
            addCommand(catchComm);
        }
        if (game.getLevel() == 8){
            addCommand(kill);
        }
        addCommand(quit);
        addCommand(help);
        addCommand(tip);
        addCommand(show);
        addCommand(moves);
        addCommand(info);
    }
    
    /**
     * Vrací seznam platných příkazů
     * @return textový řetezec s výčtem platných příkazů
     */
    public String returnValidCommands(){
        String message = "You can use: ";
        int numberOfCommands = 0;
        for(String commands: availableComms.keySet()){
            message += commands;
            numberOfCommands++;
            if(numberOfCommands != availableComms.size()){
                message += ", ";
            }
        }
        return message;
    }
}
