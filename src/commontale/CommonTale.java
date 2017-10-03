package commontale;

import logic.*;
import ui.TextInterface;

/**
 * Main třída hry CommonTale, pomocí které se hra spouští.
 * 
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public class CommonTale {

    /**
     * Main metoda spouštějící celou hru
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IGame game = new Game();
        TextInterface ui = new TextInterface(game);
        ui.play();
    }
    
    private CommonTale(){
    }
}
