package logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Třída Location představuje herní lokace ("místnosti"), mezi kterými hráč 
 * přechází. 
 * 
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public class Location {
    private String id;
    private String name;
    private final String description;
    private final String forestDescription = "Tree on the left, tree on the "
            + "right.. Yea, you're in a forest!";
    private final String crossroadDescription = "Looks like you got on a "
            + "crossroad";
    private final String cottageDescription = "You are in your grandma's "
            + "cottage";
    private final String coastDescription = "I hear a whispers of sea. Probably"
            + " you get to the coast.";
    private final String seaDescription = "You are now swimming in the sea. "
            + "Be aware of sharks!";
    private final String bridgeDescription = "There's a bridge right in front "
            + "of you.";
    private final String underBridgeDescription = "Oh, it stinks as hell! "
            + "Right, you are under the bridge..";
    private final String inFrontOfStoreDescription = "Another crossroad? Oh and"
            + " there's store on the left side.";
    private final String storeDescription = "You entered a store";
    private final String caveEntryDescription = "Dark deep cave, which is "
            + "guarded by dragon. Scary as fuck!";
    private final String secretPlaceDescription = "You made it! Welcome to the "
            + "secret marijuana garden!";
    private boolean canHoldItem = true;
    private final Map<String,Location> waysOut;
    private Map<String,Item> listOfItems;
    private boolean hasCharacter = false;
    private Character characterHere;

    private double posTop;
    private double posLeft;


    
    /**
     * Konstruktor třídy Location vytváří lokace ("místnosti") ve hře, mezi
     * kterými je možné procházet. Zadáním názvu lokace se k němu přiřadí jeho
     * popis a vytvoří se prázdný seznam východů a předmětů
     * 
     * @param id ID lokace (odpovídá šachovému rozdělení mapy - A1, A2, B1,..
     * @param name je možné vytvořit pouze tyto lokace, pomocí těchto klíčových
     * slov: forest, crossroad, cottage, coast, sea, bridge, under bridge, 
     * in front of store, store, cave entry, secret place
     */
    public Location(String id, String name, double posTop, double posLeft){
        this.id = id;
        this.name = name;
        switch(name){
            case "forest":
                this.description = forestDescription;
                break;
            case "crossroad":
                this.description = crossroadDescription;
                break;
            case "cottage":
                this.description = cottageDescription;
                break;
            case "coast":
                this.description = coastDescription;
                break;
            case "sea":
                this.description = seaDescription;
                break;
            case "bridge":
                this.description = bridgeDescription;
                break;
            case "under bridge":
                this.description = underBridgeDescription;
                this.canHoldItem = false;
                break;
            case "in front of store":
                this.description = inFrontOfStoreDescription;
                break;
            case "store":
                this.description = storeDescription;
                this.canHoldItem = false;
                break;
            case "cave entry":
                this.description = caveEntryDescription;
                this.canHoldItem = false;
                break;
            case "secret place":
                this.description = secretPlaceDescription;
                break;
            default:
                this.name = "unknown";
                this.description = "This place doesn't exist";
        }
        this.waysOut = new HashMap<>();
        this.listOfItems = new HashMap<>();

        this.setPosTop(posTop);
        this.setPosLeft(posLeft);
    }
    
    /**
     * Metoda přidá k dané lokaci její východ. Zadává se směr (sever, jih, 
     * východ, západ) a lokace na této straně
     * @param direction povolené hodnoty N,S,W,E 
     * @param location  sousední lokace
     */
    public void addWay(String direction, Location location){
        waysOut.put(direction, location);
    }
    
    /**
     * Metoda přiřadí do dané lokace zadaný předmět. Zadává se instance předmětu
     * @param item předmět samotný
     */
    public void addItem(Item item){
        listOfItems.put(item.getName(), item);
        item.setLocation(this);
    }
    
    /**
     * Metoda pro vymazání předmětu z lokace, používá se zejména třídou GamePlan
     * při updatování pohyblivých předmětů
     * @param item 
     */
    public void removeItem(Item item){
        listOfItems.remove(item.getName());
    }
    
    /**
     * Metoda, která vezme předmět z dané lokace. Předmět je vrácen metodou a
     * odstraněn ze seznamu předmětů lokace.
     * @param name jméno předmětu, který chceme sebrat
     * @return pokud je předmět dostupný vrátí předmět jako objekt, jinak null
     */
    public Item grabItem(String name){
        if(listOfItems.containsKey(name)){
            return listOfItems.remove(name);
        }
        else{
        return null;
        }
    }
    
    /**
     * Metoda vrací předmět bez jeho smazání z dané lokace
     * @param name název předmětu
     * @return instance předmětu (pokud zde není, tak null)
     */
    public Item returnItem(String name){
        if(listOfItems.containsKey(name)){
            return listOfItems.get(name);
        }
        else{
        return null;
        }
    }
    
    /**
     * Tato metoda vrací logickou hodnotu, zda se v dané lokaci vyskytuje 
     * zadaný předmět
     * @param name název předmětu
     * @return true pokud daný předmět je v lokaci, false pokud ne
     */
    public boolean hasItem(String name){
        return listOfItems.containsKey(name);
    }
    
    /**
     * Metoda přiřadí do dané lokace postavu.
     * @param character postava samotná
     */
    public void addCharacter(Character character){
        this.characterHere = character;
        character.setLocation(this);
        hasCharacter = true;
    }
    
    /**
     * Vrací postavu nacházející se v dané lokaci
     * @return postava v této lokaci
     */
    public Character returnCharacter(){
        return characterHere;
    }
    
    /**
     * Vymaže postavu z dané lokace, pokud v lokaci tato postava je. Používá se 
     * zejména pro přesun postav.
     * @param character postava, která má být vymazána
     */
    public void removeCharacter(Character character){
        if(character.equals(characterHere)){
        this.characterHere = null;
        character.setLocation(null);
        hasCharacter = false;
        }
    }
    
    /**
     * Vrátí logickou hodnotu, zda se v dané lokaci nachází postava či nikoli
     * @return true, pokud v dané lokaci je postava, false pokud ne
     */
    public boolean getHasCharacter(){
        return hasCharacter;
    }
    
    /**
     * Vrátí logickou hodnotu, zda se v dané lokaci nachází zadaná postava
     * @param name jméno postavy
     * @return true, pokud se tato postava nachází v lokaci
     */
    public boolean getHasCharacter(String name){
        return characterHere.getName().equals(name);
    }
    
    /**
     * Vrátí lokaci v daném směru (sever, východ, jih, západ)
     * @param direction směr ve formátu N,E,S nebo W
     * @return vrátí lokaci v zadaném směru
     */
    public Location getNextLocation(String direction){
        return waysOut.get(direction);
    }
    
    /**
     * Vrací logickou hodnotu, zda v daném směru existuje lokace, do které by 
     * mohl hráč jít.
     * @param direction směr ve formátu N,E,S nebo W
     * @return T/F, jestli lokace ve směru existuje
     */
    public boolean haveWay(String direction){
        return waysOut.containsKey(direction);
    }

    public String getDirection(Location location){
        String[] directions = {"N","E","S","W"};
        for(String direction: directions){
            if(this.haveWay(direction))
                if(this.getNextLocation(direction).equals(location))
                    return direction;
        }
        return "N";
    }
    
    
    /**
     * Vrací jméno lokace
     * @return jméno lokace
     */
    public String getName(){
        return name;
    }
    
    /**
     * Vrátí ID lokace
     * @return ID lokace (A1,A2,B1,C3,..)
     */
    public String getID(){
        return id;
    }
    
    /**
     * Vrátí seznam dostupných východů z aktuání lokace společně se směry
     * 
     * @return textový řetezec dostupných východů oddělených čárkami
     */
    public String returnWaysOut(){
        String ways = "\n" + "You can go.. ";
        boolean comma = false;
        if(this.waysOut.containsKey("N") && 
                !this.waysOut.get("N").getName().equals("secret place")){
            ways += this.waysOut.get("N").getName() + " (north)";
            comma = true;
        }
        if(this.waysOut.containsKey("E") && 
                !this.waysOut.get("E").getName().equals("secret place")){
            if(comma){
                ways += ", ";
            }
            ways += this.waysOut.get("E").getName() + " (east)";
            comma = true;
        }
        if(this.waysOut.containsKey("S") && 
                !this.waysOut.get("S").getName().equals("secret place")){
            if(comma){
                ways += ", ";
            }
            ways += this.waysOut.get("S").getName() + " (south)";
            comma = true;
        }
        if(this.waysOut.containsKey("W") && 
                !this.waysOut.get("W").getName().equals("secret place")){
            if(comma){
                ways += ", ";
            }
            ways += this.waysOut.get("W").getName() +  " (west)";
        }
        return ways;
    }
    
    /**
     * Vrátí seznam předmětů v dané lokaci
     * @return textový řetezec předmětů v dané lokaci oddělených čárkami
     */
    public String itemsHere(){
        String items = "";
        if (!listOfItems.isEmpty()){
            items += "\n" + "There's ";                    
            int comma = 1;
            int numberOfItems = listOfItems.size();
            for (String itemsHere: listOfItems.keySet()){
                items += itemsHere;
                if (comma < numberOfItems){
                    items += ", ";
                    comma++;
                }
            }
            items += " laying on the ground.";
        }
        return items;
    }
    
    /**
     * Pokud je v dané lokaci postava, vrátí zprávu, jaká postava se zde nachází
     * @return větu, jaká postava se nechází v této lokaci
     */
    public String charactersHere(){
        String chars = "";
        if (!(characterHere == null)){
            chars += "And " + characterHere.getName() + " is walking around";
        }
        return chars;    
    }
    
    /**
     * Vrací zprávu, která se má zobrazit uživateli při příchodu do dané lokace
     * @return zpráva pro výstup
     */
    public String message(){
        String message = description;
        if (canHoldItem){
            message += " " + itemsHere() + " " + charactersHere();
        }
        message += returnWaysOut();
        return message;
    }
    
    /**
     * Metoda equals pro porovnání dvou lokací. Překrývá se metoda equals ze
     * třídy Object. Dvě lokace jsou shodné, pokud mají stejné ID.
     *
     * Bližší popis metody equals je u třídy Object.
     *
     * @param     o object, který se má porovnávat s aktuálním
     * @return    hodnotu true, pokud má zadaná lokace stejné ID, jinak false
     */  
      @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci
        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr 
        if (!(o instanceof Location)) {
            return false;    // pokud parametr není typu Lokace, vrátíme false
        }
        // přetypujeme parametr na typ Lokace 
        Location second = (Location) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou ID. 
        //Vrátí true pro stejné ID a i v případě, že jsou obě ID null,
        //jinak vrátí false.

       return (java.util.Objects.equals(this.id, second.id));       
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
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }


    public double getPosTop() {
        return posTop;
    }

    public double getPosLeft() {
        return posLeft;
    }

    public void setPosTop(double posTop) {
        this.posTop = posTop;
    }

    public void setPosLeft(double posLeft) {
        this.posLeft = posLeft;
    }
}