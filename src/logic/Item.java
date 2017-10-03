package logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Třída Item představuje předměty ve hře, které je možné vzít, zahodit a 
 * manipulovat s nimi.
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public class Item {
    private String name;
    private final String description;
    private Location currentLocation;
    private final String poleDesc = "Some kind of pole";
    private final String plankDesc = "Some kind of plank";
    private final String canDesc = "Can can be used to catch a squirrel";
    private final String rumDesc = "Rum can be used to get homeless drunk (he "
            + "won't rob you then)";
    private final String keyDesc = "Key to the chest";
    private final String scissorsDesc = "Scissors may be used to get a weed";
    private final String woodDesc = "Just wood from forest";
    private final String moneyDesc = "You can buy a meat for money in the "
            + "store";
    private final String meatDesc = "Meat used to prepare a sandwich";
    private final String sandwichDesc = "Sandwich for the stoned man";
    private final String weedDesc = "A bit of marijuana for the imp";
    private final String oxygenDesc = "Oxygen bomb, which allow you to enter "
            + "the sea";
    private final String swordDesc = "A mystic sword, with which you can kill "
            + "the dragon!";
    private final String chestDesc = "Just a chest with a lock";
    private int keyCounter = 0;
    private int chestCounter = 0;
    private Map<String,Item> chestContent;
    private Item key;
    private boolean chestOpen = false;
    
    /**
     * Konstruktor vytvoří předměty a přiřadí k nim popis
     * @param name název předmětu, můžou být použity jen tyto hodnoty: pole,
     * plank, can, rum, key, scissors, wood, money, meat, sandwich, weed, sword,
     * oxygen, chest
     */
    public Item(String name){
        this.name = name;
        switch(name){
            case "pole":
                this.description = poleDesc;
                break;
            case "plank":
                this.description = plankDesc;
                break;
            case "can":
                this.description = canDesc;
                break;
            case "rum":
                this.description = rumDesc;
                break;
            case "key":
                if (keyCounter > 0){
                    this.name += Integer.toString(keyCounter);
                }
                keyCounter++;
                this.description = keyDesc;
                break;
            case "scissors":
                this.description = scissorsDesc;
                break;
            case "wood":
                this.description = woodDesc;
                break;
            case "money":
                this.description = moneyDesc;
                break;
            case "meat":
                this.description = meatDesc;
                break;
            case "sandwich":
                this.description = sandwichDesc;
                break;
            case "weed":
                this.description = weedDesc;
                break;
            case "oxygen":
                this.description = oxygenDesc;
                break;
            case "sword":
                this.description = swordDesc;
                break;
            case "chest":
                if (chestCounter > 0){
                    this.name += Integer.toString(chestCounter);
                }
                this.description = chestDesc;
                chestContent = new HashMap<>();
                break;
            default:
                this.name = "unknown";
                this.description = "Item doesn't exist";
        }
    }
    
    /**
     * Vrací jméno předmětu
     * @return jméno předmětu
     */
    public String getName(){
        return name;
    }
    
    /**
     * Uloží k předmětu jeho aktuální pozici
     * @param location 
     */
    public void setLocation(Location location){
        this.currentLocation = location;
    }
    
    /**
     * Vrátí aktuální pozici předmětu
     * @return lokace ve které se předmět nachází
     */
    public Location getLocation(){
        return currentLocation;
    }
    
    /**
     * Vrátí popis předmětu
     * @return popis předmětu
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * Přiřadí k truhle klíč, který ji otevře
     * @param key klíč k truhle
     */
    public void addKey(Item key){
        this.key = key;
    }
    
    /**
     * Metoda odemkne truhlu při zadání správného klíče jako parametru.
     * @param key klíč k truhle
     * @return true pokud byla truhla otevřena, false pokud nebyl otevřena
     */
    public boolean openChest(Item key){
        if (this.key.equals(key)){
            chestOpen = true;
        }
        return chestOpen;
    }
    
    /**
     * Metoda, která vrací logickou hodnotu, zda je truhla otevřená
     * @return true pokud je truhla otevřená
     */
    public boolean chestOpened(){
        return chestOpen;
    }
        
    /**
     * Přidá předmět do truhly a uloží do něj pozici truhly
     * @param item předmět pro přidání do truhly
     */
    public void addItemToChest(Item item){
        chestContent.put(item.getName(), item);
        item.setLocation(currentLocation);
    }
    
    /**
     * Vrátí textový řetezec vracející výpis předmětů, které truhla obsahuje
     * @return výpis předmětů v truhle
     */
    public String returnItemsInChest(){
        String items = "";
        if (!chestContent.isEmpty()){
            items += "\n" + "There's ";
            int comma = 1;
            int numberOfItems = chestContent.size();
            for (String itemsHere: chestContent.keySet()){
                items += itemsHere;
                if (comma < numberOfItems){
                    items += ", ";
                    comma++;
                }
            }
            items += " in the chest. Items has dropped on the ground.";
        }
        else{
            items += "Chest is empty!";
        }
        return items;
    }
    
    /**
     * Vrátí obsah truhly
     * @return kolekce obsahu truhly
     */
    public Collection<Item> returnChestContent(){
        if(chestOpen){
        return this.chestContent.values();
        }
        else {
        Collection<Item> emptyCollection = new ArrayList<>();
        return emptyCollection;
        }
    }
            
    /**
     * Metoda equals pro porovnání dvou předmětů. Překrývá se metoda equals ze
     * třídy Object. Dva předměty jsou shodné, pokud mají stejný název.
     *
     * Bližší popis metody equals je u třídy Object.
     *
     * @param     o object, který se má porovnávat s aktuálním
     * @return    rue, pokud má zadaný předmět stejný název, jinak false
     */  
    @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci
        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr 
        if (!(o instanceof Item)) {
            return false;    // pokud parametr není typu Item, vrátíme false
        }
        // přetypujeme parametr na typ Item 
        Item second = (Item) o;

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
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.name);
        return hash;
    }
}
