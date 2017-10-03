package logic.commands;

/**
 * Rozhraní pro jednotlivé třídy, která zpracovávají příkazy ve hře
 * 
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public interface ICommand {
    /**
     * Metoda pro provedení příkazu ve hře.
     * Počet parametrů je závislý na konkrétním příkazu - např. příkazy end,
     * help, nebo moves nemají parametry.
     *
     * @param  parametres počet parametrů závisí na konkrétním příkazu
     * @return vrací zprávu pro UI
     */
    public String execute(String... parametres);
    
    /**
     * Metoda pro zpracování odpovědi v režimu rozhovoru. 
     * @param answer odpověď z konzole
     * @return vrací zprávu pro UI
     */
    public String answer(String answer);
    
    /**
     * Metoda vrací název příkazu (slovo, které používá hráč pro jeho vyvolání)
     *
     * @return    nazev prikazu
     */
    public String getName();
}
