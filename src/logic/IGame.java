package logic;

/**
 * Rozhraní které musí implementovat hra, je na ně navázáno uživatelské rozhraní
 *
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public interface IGame {
    /**
     * Vrátí úvodní zprávu pro hráče.
     *
     * @return    vrací se řetězec, který se má vypsat na obrazovku
     */
    public String returnWelcome();
    
    /**
     * Vrátí závěrečnou zprávu pro hráče.
     *
     * @return    vrací se řetězec, který se má vypsat na obrazovku
     */
    public String returnEpilogue();
    
    /**
     * Vrací informaci o tom, zda hra již skončila bez ohledu na to, jestli
     * hráč vyhrál, prohrál nebo zadal příkaz quit
     *
     * @return    vrací true, pokud hra skončila
     */
     public boolean isOver();
     
    /**
     * Metoda zpracuje zadaný řetezec tak, že ho rozdělí na slova a zjistí 
     * jaký příkaz se má vykonat (první 1-2 slova). Pokud příkaz existuje pošle
     * mu parametry (další slova) a provede ho.
     *
     * @param     line text, který zadal uživatel jako příkaz do hry
     * @return    vrací se řetězec, který se má vypsat na obrazovku
     */
     public String compileCommand(String line);
   
    
    /**
     * Metoda vrátí odkaz na herní plán, je využita hlavně v testech,
     * kde se jejím prostřednictvím získává aktualní místnost hry.
     *
     * @return    odkaz na herní plán
     */
     public GamePlan getGamePlan();

     public boolean getTalking();

}
