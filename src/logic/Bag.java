/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import util.Observer;
import util.Subject;

import java.util.*;

/**
 * Třída Bag představuje batoh, který nosí hráč u sebe a dává do něj předměty.
 * Batoh má kapacitu na 2 věci, pokud má hráč batoh plný, musí nějakou věc 
 * zahodit. Batoh je na začátku hry v logice zamčený (atribut locked), v rámci
 * příběhu hráč batoh ještě nemá, jakmile ho získá, bude zde odemčen.
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public class Bag implements Subject{
    private boolean locked;
    private final Map<String,Item> bagContent;
    private final GamePlan gamePlan;

    private List<Observer> listObserver = new ArrayList<>();
    
    /**
     * Konstuktror třídy Batoh
     * @param gamePlan herní pláb
     */
    public Bag(GamePlan gamePlan){
        this.gamePlan = gamePlan;
        bagContent = new HashMap<>();
        locked = true;
    }
    
    /**
     * Pokud hráč má batoh a není plný (kapacita 2 předměty), přidá předmět do
     * batohu. 
     * @param name jméno předmětu
     * @return zpráva pro hráče
     */
    public String addItem(String name){
        if(locked){
            return "You don't have a bag";
        }        
        if(bagContent.size() < 2){
            Item item = gamePlan.getCurrentLocation().grabItem(name);
            bagContent.put(item.getName(), item);
            item.setLocation(null);
            notifyObservers();
            return item.getName() + " added to bag!";
        }
        else{
            return "Your bag is full! You need to throw something away.";
        }
    }
    
    /**
     * Pokud hráč má batoh a není plný (kapacita 2 předměty), přidá předmět do
     * batohu. 
     * @param item instance předmětu
     * @return zpráva pro hráče
     */
    public String addItem(Item item){
        if(locked){
            return "You don't have a bag";     
        }   
        if(bagContent.size() < 2){
            bagContent.put(item.getName(), item);
            item.setLocation(null);
            notifyObservers();
            return item.getName() + " added to bag!";
        }
        else{
            return "Your bag is full! You need to throw something away.";
        }
    }
    
    /**
     * Pokud je zadaný předmět v batohu, tato metoda ho z batohu odstraní a 
     * přidá ho do aktuální lokace
     * @param item název předmětu k vyhození
     * @return textový řetezec s výsledkem vyhození
     */
    public String throwItem(String item){
        if (bagContent.containsKey(item)){
            gamePlan.getCurrentLocation().addItem(bagContent.remove(item));
            notifyObservers();
            return item + " was thrown";
        }
        else{
            return "You don't have this kind of item in your bag";
        }
    }
    
    /**
     * Vrátí jako textový řetězec seznam věcí v batohu
     * @return seznam věcí v batohu
     */
    public String returnBagContent(){
        String items = "";
        if (!bagContent.isEmpty()){
            items += "There's ";
            int comma = 1;
            int numberOfItems = bagContent.size();
            for (String itemsHere: bagContent.keySet()){
                items += itemsHere;
                if (comma < numberOfItems){
                    items += ", ";
                    comma++;
                }
            }
            items += " in your bag";
        }
        else{
            items += "Your bag is empty!";
        }
        return items;
    }

    public Set<String> getBagContent(){
        return bagContent.keySet();
    }
    
    /**
     * Odemkne batoh - v příbehu značí moment, kdy hráč batoh dostane
     */
    public void unlockBag(){
        locked = false;
        //notifyObservers();
    }
    
    /**
     * Vrací stav batohu
     * @return true pokud je zamčený, false pokud není
     */
    public boolean getLocked(){
        return locked;
    }
    
    /**
     * Vrací počet věci v batohu
     * @return počet věcí v batohu
     */
    public int bagContentCount(){
        return bagContent.size();
    }
    
    /**
     * Metoda pro použití předmětu z batohu. Pokud věc v batohu je, smaže ji z
     * batohu a vrátí ji. Pokud ne, vrací null
     * @param name název předmětu
     * @return předmět z batohu, pokud takový předmět v batohu není, vrací null
     */
    public Item getItem(String name){
        if(bagContent.containsKey(name)){
            return bagContent.remove(name);
        }
        else{
            return null;
        }
    }
    
    /**
     * Metoda vrátí předmět z batohu bez odstranění
     * @param name název předmětu
     * @return instance předmětu
     */
    public Item returnItem(String name){
        if(bagContent.containsKey(name)){
            return bagContent.get(name);
        }
        else{
            return null;
        }
    }
    
    /**
     * Metoda vymaže všechny předměty z batohu (je využíváná zejména postavou
     * bezdomovec)
     */
    public void removeItems(){
        bagContent.clear();
    }
    
    /**
     * Vrací logickou hodnotu, zda batoh obsahuje daný předmět
     * @param name název předmětu
     * @return true, pokud batoh obsahuje předmět
     */
    public boolean containsItem(String name){
        return bagContent.containsKey(name);
    }

    @Override
    public void registerObserver(Observer observer) {
        listObserver.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        listObserver.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer item : listObserver){
            item.update();
        }
    }
}
