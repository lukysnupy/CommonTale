/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Třída Character představuje postavy ve hře. S postavama je možné mluvit,
 * některé mohou držet předměty a některé vám můžou dát radu pro další postup
 * ve hře.
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public class Character {
    private final String name;
    private final String[] firstSpeech;
    private boolean asken = false;
    private boolean hasItem = false;
    private Item item;
    private String answerItem;
    private String answerNoItem;
    private boolean canGiveItem = false;
    private final String[] advice;
    private String answerSecretPlace;
    private final boolean changeableSpeech;
    private String neededItem;
    private String answerNotItemINeed;
    private String answerThankForItem;
    private String itemForExchange;
    private boolean wantItem = false;
    private String answerDontWantItem;
    private String goodBye;
    private List<Location> validLocations;
    private final Game game;
    private String[] puzzles;
    private String[] answersForPuzzles;
    private int puzzleNumber;
    private Location location;
    private final boolean actionable;
    
    /**
     * Konstuktor postavy ve hře. Jako parametr je nutné zadat hru samotnou, 
     * herní plán, jméno postavy a logickou hodnotu, zda se jedná o postavu s 
     * proměnlivými odpověďmi v průběhu hry, či nikoli
     * @param game hra samotná
     * @param name jméno postavy
     * @param changeableSpeech true pokud postava mění odpovědi, false pokud ne
     * @param actionable logická hodnota, zda postava ihned začne mluvit, když 
     *                   hráč přijde do lokace v které se nachází
     */
    public Character(Game game, String name, 
            boolean changeableSpeech, boolean actionable){
        this.game = game;
        this.name = name;
        if(changeableSpeech){
            firstSpeech = new String[2];
            advice = new String[4];
        }
        else{
            firstSpeech = new String[1];
            advice = new String[1];
        }
        this.changeableSpeech = changeableSpeech;
        this.actionable = actionable;
    }
    
    /**
     * Metoda vrací úvodní řeč postavy při oslovení
     * @return úvodní řeč postavy
     */
    public String returnFirstSpeech(){
        if(!changeableSpeech || !asken){
            asken = true;
            return firstSpeech[0];
        }
        else{
            return firstSpeech[1];
        }
    }
    
    /**
     * Metoda vrací větu na rozloučenou při ukončení rozhovoru
     * @return věta na rozloučenou
     */
    public String returnGoodBye(){
        return goodBye;
    }
    
    /**
     * Metoda vrací radu pro další postup ve hře. Volá se po tom, co hráč zadá 
     * příslušný příkaz
     * @return rada
     */
    public String returnAdvice(){
        if(!changeableSpeech || game.getLevel() == 2){
            return advice[0];
        }
        if(game.getLevel() > 2 && game.getLevel() < 5){
            return advice[1];
        }
        if(game.getLevel() >= 5 && game.getLevel() < 8){
            return advice[2];
        }
        return advice[3];
    }
    
    /**
     * Metoda vrací odpověď na otázku zda má postava nějaký předmět pro hráče.
     * Samotné předání itemu se řeší v metodě giveItem().
     * @return odpověď k vypsání
     */
    public String returnItemSpeech(){
        canGiveItem = false;
        if(!hasItem){
            return answerNoItem;
        }
        if(game.fullBag()){
            return "What do you want when your bag is full? Throw something"
                    + "away and than ask me..";
        }
        canGiveItem = true;
        return answerItem;
    }
    
    /**
     * Metoda vrací logickou hodnotu, zda je možné hráči předat předmět či ne
     * @return logická hodnota, zda je možnost předat předmět
     */
    public boolean getCanGiveItem(){
        return canGiveItem;
    }
    
    /**
     * Metoda vrací předmět postavy. Zároveň nastaví atribut postavy na "nemá 
     * předmět".
     * @return předmět postavy pro předání hráči
     */
    public Item giveItem(){
        hasItem = false;
        return item;
    }
    
    /**
     * Vrací logickou hodnotu, zda zadaný předmět je ten, který postava chce či
     * nikoli
     * @param item dávaný předmět
     * @return true pokud je to předmět, který postava potřebuje, false pokud ne
     */
    public boolean neededItem(String item){
        return item.equals(neededItem);
    }
    
    /**
     * Metoda vrací odpověď postavy na předání zadaného předmětu.
     * @param item zadaný předmět
     * @return odpověď postavy
     */
    public String obtainItem(String item){
        if (!neededItem(item)){
            return answerNotItemINeed;
        }
        return answerThankForItem;
    }
    
    /**
     * Vrací název předmětu, který má hráč dostat výměnou za darovaný předmět.
     * @return název předmětu, který má hráč dostat
     */
    public String returnExchangedItem(){
        return itemForExchange;
    }
    
    /**
     * Vrátí logickou hodnotu, zda postava chce od hráče předmět či nikoli
     * @return true, pokud postava chce nějaký předmět
     */
    public boolean getWantItem(){
        return wantItem;
    }
    
    /**
     * Vrátí odpověď postavy na nabídnutí předmětu hráčem v případě, že žádný 
     * předmět nechce
     * @return odpověď hráči
     */
    public String returnDontWantItem(){
        return answerDontWantItem;
    }
    
    /**
     * Přiřadí úvodní proslov k postavě. Jednoparametrová metoda je určená pro
     * postavy s neměnými odpověďmi v průběhu hry.
     * @param speech1 úvodní proslov postavy
     */
    public void addFirstSpeech(String speech1){
        firstSpeech[0] = speech1;
        if(changeableSpeech){
            firstSpeech[1] = speech1;
        }
    }
    
    /**
     * Přiřadí úvodní proslov k postavě. Určeno zejména pro postavy s měnícími
     * se odpověďmi v průběhu hry.
     * @param speech1 úvodní proslov 
     * @param speech2 úvodní proslov, když už byla postava alespoň jednou 
     *                oslovena
     */
    public void addFirstSpeech(String speech1, String speech2){
        firstSpeech[0] = speech1;
        if(changeableSpeech){
            firstSpeech[1] = speech2;
        }
    }
    
    /**
     * Přiřadí postavě předmět, který může hráč od postavy dostat
     * @param item instance předmětu
     * @param answerItem odpověď při předávání předmětu
     * @param answerNoItem odpověď, pokud již postava předmět nemá
     */
    public void addItem(Item item, String answerItem, String answerNoItem){
        hasItem = true;
        this.item = item;
        this.answerItem = answerItem;
        this.answerNoItem = answerNoItem;
    }
    
    /**
     * Přiřadí postavě bez předmětu odpověď pro hráče, pokud se zeptá na předmět
     * @param answerNoItem odpověď postavy na otázku, zda má předmět
     */
    public void addNoItemAnswer(String answerNoItem){
        hasItem = false;
        this.answerNoItem = answerNoItem;
    }
    
    /**
     * Přiřadí k jedné vybrané postavě informaci o poloze tajného místa. 
     * Ostatním předá informaci o tom, jaká postava ví, kde se místo nachází.
     * @param character postava, která má vědět polohu tajného místa
     * @param location světová strana, na kterou leží tajné místo 
     *                 (ve formátu north, south, east, west)
     */
    public void addSecretPlace(Character character, String location){
        if(character.equals(this)){
            answerSecretPlace = "You need to go " + location + " of the "
                    + location + "ernmost place";
        }
        else{
            answerSecretPlace = "You should probably go and ask " +
                    character.getName();
        }
    }
    
    /**
     * Přiřadí postavě radu, kterou může dát hráči pro další postup ve hře.
     * Jednoparametrová metoda je určená pro postavy s neměnými odpověďmi v 
     * průběhu hry.
     * @param advice rada pro hráče
     */
    public void addAdvice(String advice){
        this.advice[0] = advice;
        if(changeableSpeech){
            for (int i = 1; i < 4; i++) {
                this.advice[i] = advice;
            }
        }
    }
    
    /**
     * Přidá postavě rady, které může dát hráči pro další postup ve hře. Tato
     * metoda je určená pro postavy s měnícími se odpověďmi v průběhu hry.
     * @param advice1 rada pro "level" 2 (jak najít kyslíkovou bombu)
     * @param advice2 rada pro "level" 5 (jak získat maso)
     * @param advice3 rada pro "level" 8 (povzbuzení pro zabití draka)
     */
    public void addAdvice(String advice1, String advice2, String advice3){
        advice[0] = advice1;
        if(changeableSpeech){
            advice[1] = "You're looking for the secret place? " + 
                    answerSecretPlace;
            advice[2] = advice2;
            advice[3] = advice3;
        }
    }
    
    /**
     * Tato metoda přidá postavě předmět, který daná postava chce od hráče. 
     * Výměnou za to dá hráči nějaký jiný předmět.
     * @param neededItem název předmětu, který postava chce po hráči
     * @param answerNotItemINeed odpověď, pokud hráč dává postavě předmět, který
     *                           nechce
     * @param answerThankForItem odpověď, pokud postava dostane předmět, který
     *                           chce
     * @param itemForExchange název předmětu, který dá postava hráči jako výměnu
     */
    public void addExchange(String neededItem, String answerNotItemINeed,
            String answerThankForItem, String itemForExchange){
        wantItem = true;
        this.neededItem = neededItem;
        this.answerNotItemINeed = answerNotItemINeed;
        this.answerThankForItem = answerThankForItem;
        this.itemForExchange = itemForExchange;
    }
    
    /**
     * Tato metoda přidá postavě, která nevyměňuje předměty, odpověď pro hráče,
     * který jí předmět nabídne
     * @param answerDontWantItem odpověď pro hráče 
     */
    public void addNoExchangeAnswer(String answerDontWantItem){
        wantItem = false;
        this.answerDontWantItem = answerDontWantItem;
    }
    
    /**
     * Tato metoda přidá postavě rozloučení
     * @param goodBye rozloučení
     */
    public void addGoodBye(String goodBye){
        this.goodBye = goodBye;
    }
    
    /**
     * Přidá hádanky postavě (tato metoda bude využívána jen pro postavu 
     * babička - grandma)
     * @param puzzles hadánky
     */
    public void addPuzzles(String... puzzles){
        this.puzzles = new String[puzzles.length];
        for (int i = 0; i < puzzles.length; i++) {
            this.puzzles[i] = puzzles[i];
        }
    }
    
    /**
     * Přidá odpovědi hádankám (stejně jako předchozí metoda je i tato jen pro
     * postavu babička - grandma)
     * @param answersForPuzzles odpovědi na hádanky
     */
    public void addAnswersForPuzzles(String... answersForPuzzles){
        this.answersForPuzzles = new String[answersForPuzzles.length];
        for (int i = 0; i < answersForPuzzles.length; i++) {
            this.answersForPuzzles[i] = answersForPuzzles[i];
        }
    }
    
    /**
     * Vrací hádanku
     * @param puzzleNumber číslo hádanky
     * @return textový řetezec s hádankou
     */
    public String returnPuzzle(int puzzleNumber){
        this.puzzleNumber = puzzleNumber;
        return puzzles[puzzleNumber];
    }
    
    /**
     * Kontroluje správnou odpověď na hádanku
     * @param answer odpověď na hádanku od hráče
     * @return true, pokud je odpověď správná, false pokud ne
     */
    public boolean checkAnswerForPuzzle(String answer){
        if (answersForPuzzles[puzzleNumber].equals(answer)){
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * Tato metoda přidá postavám, které se přemisťují po mapě všechny lokace
     * zadané v parametrech.
     * @param locations lokace, kde se může postava nacházet v průběhu hry
     */
    public void addValidLocations(Location... locations){
        validLocations = new ArrayList<>();
        validLocations.addAll(Arrays.asList(locations));
    }
    
    /**
     * Vrátí náhodnou lokaci ze seznamu platných lokací pro danou postavu.
     * @return náhodná lokace
     */
    public Location returnShuffleLocation(){
        Collections.shuffle(validLocations);
        return validLocations.get(0);
    }
    
    /**
     * Nastaví aktuální lokaci postavy na zadanou
     * @param location lokace postavy
     */
    public void setLocation(Location location){
        this.location = location;
    }
    
    /**
     * Vrací aktuální lokaci postavy
     * @return aktuální lokace postavy
     */
    public Location getLocation(){
        return location;
    }
    
    /**
     * Vrací jméno postavy
     * @return jméno postavy
     */
    public String getName(){
        return name;
    }
    
    /**
     * Vrací logickou hodnotu, zda postava při návštěve její lokace ihned začne 
     * mluvit či nikoli
     * @return true, pokud má začít hned mluvit, false pokud ne
     */
    public boolean getActionable(){
        return actionable;
    }
    
    /**
     * Metoda equals pro porovnání dvou postav. Překrývá se metoda equals ze
     * třídy Object. Dvě postavy jsou shodné, pokud mají stejný název.
     *
     * Bližší popis metody equals je u třídy Object.
     *
     * @param     o object, který se má porovnávat s aktuálním
     * @return    true, pokud má zadaná postava stejný název, jinak false
     */  
    @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci
        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr 
        if (!(o instanceof Character)) {
            return false;    //pokud parametr není typu Character, vrátíme false
        }
        // přetypujeme parametr na typ Character 
        Character second = (Character) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů. 
        //Vrátí true pro stejný název a i v případě, že jsou oba názvy null,
        //jinak vrátí false.

       return (java.util.Objects.equals(this.name, second.name));       
    }
    
    /**
     * Metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object.
     * @return hash
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.name);
        return hash;
    }
    
    
    
}
