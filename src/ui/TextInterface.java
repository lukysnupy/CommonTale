package ui;

import java.util.Scanner;
import logic.IGame;

/**
 * Tato třída představuje uživatelské rozhraní hry "Common Tale". Třída vytváří
 * instanci třídy Game, která představuje logiku hry. Čte vstup zadaný 
 * uživatelem, předává tento řetězec logice a vypisuje odpovědi logiky na 
 * konzoli.
 * 
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public class TextInterface {
    private IGame game;
    
    /**
     * Konstruktor třídy vytvářející hru
     * 
     * @param game 
     */
    public TextInterface(IGame game){
        this.game = game;
    }
    
    /**
     * Hlavní metoda hry. Vypíše úvodní text a pak opakuje čtení a zpracovávání
     * příkazů od hráče do konce hry (dokud metoda konecHry() z logiky nevrátí
     * hodnotu true). Nakonec vypíše text epilogu.
     */
    public void play() {
        System.out.println(game.returnWelcome());
        
        // Základní cyklus programu - opakovaně se čtou příkazy a poté
        // se provádějí do konce hry.

        while (!game.isOver()) {
            String line = readLine();
            System.out.println(game.compileCommand(line));
        }

        System.out.println(game.returnEpilogue());
    }
    
    /**
     * Metoda přečte příkaz z příkazového řádku
     *
     * @return    vrací přečtený příkaz jako instanci třídy String
     */
    public String readLine(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        return scanner.nextLine();
    }
    
}
