package logic;

import util.Subject;
import util.Observer;

import java.util.*;

/**
 * Třída představující herní plán. Spouští se při startu hry a vytváří herní
 * mapu, rozmisťuje předměty a postavy.
 * 
 * @author  Lukas Ruzicka
 * @version LS 2016/2017
 */
public class GamePlan implements Subject{
    private Location currentLocation;
    private Location previousLocation;
    private final Game game;
    private Map<String,Item> unpositionedItems;
    private Location cottage;
    private Location forest1;
    private Location coast;
    private Location sea1;
    private Location sea2;
    private Location crossroad;
    private Location forest2;
    private Location forest3;
    private Location forest4;
    private Location bridge;
    private Location underBridge;
    private Location inFrontOfStore;
    private Location store;
    private Location forest5;
    private Location caveEntry;
    private Location secretPlace;
    private String secretPlacePosition;
    private Item pole;
    private Item plank;
    private Item can;
    private Item rum;
    private Item rum1;
    private Item rum2;
    private Item key;
    private Item key1;
    private Item scissors;
    private Item wood;
    private Item money;
    private Item meat;
    private Item sandwich;
    private Item weed;
    private Item weed1;
    private Item sword;
    private Item oxygen;
    private Item chest;
    private Item chest1;
    private Character grandma;
    private Character fisherman;
    private Character shepherd;
    private Character shopgirl;
    private Character pothead;
    private Character imp;
    private Character homeless;
    private boolean homelessOnMap;
    private Character shark;
    private boolean sharkOnMap;
    private Character dragon;
    private Character charSecretPlace;

    private List<Observer> listObserver = new ArrayList<>();
    
    
    /**
     * Konstruktor metody GamePlan zavolá vlastní metody, které vytvoří lokace,
     * postavy a umístí předměty do mapy.
     * @param game instance hry
     */
    public GamePlan(Game game){
        this.game = game;
        createMap();
        createCharacters();
        addItems();

    }
    
    /**
     * Metoda vytvoří mapu hry - založí lokace a přiřadí jim sousední pole.
     */
    private void createMap(){
        //Založí všechny lokace hry
        cottage = new Location("A1","cottage",100,80);
        currentLocation = cottage;
        previousLocation = currentLocation;
        forest1 = new Location("A2","forest",100,165);
        coast = new Location("A3","coast",100,250);
        sea1 = new Location("A4","sea",30,290);
        sea2 = new Location("A5","sea",70,330);
        crossroad = new Location("B1","crossroad",170,80);
        forest2 = new Location("B2","forest",170,165);
        forest3 = new Location("B3","forest",170,250);
        forest4 = new Location("C1","forest",240,80);
        bridge = new Location("C2","bridge",215,165);
        underBridge = new Location("C3","under bridge",240,180);
        inFrontOfStore = new Location("C4","in front of store",240,250);
        store = new Location("C5","store",215,250);
        forest5 = new Location("D1","forest",300,250);
        caveEntry = new Location("F1","cave entry",300,180);
        secretPlace = new Location("SP","secret place",0,0);
        
        //Přidá každé lokaci jejich sousední lokace
        cottage.addWay("S", crossroad);
        forest1.addWay("E", coast);
        forest1.addWay("S", forest2);
        coast.addWay("W", forest1);
        coast.addWay("N", sea1);
        coast.addWay("E", sea2);
        coast.addWay("S", forest3);
        sea1.addWay("E", sea2);
        sea1.addWay("S", coast);
        sea2.addWay("N", sea1);
        sea2.addWay("W", coast);
        crossroad.addWay("N", cottage);
        crossroad.addWay("E", forest2);
        crossroad.addWay("S", forest4);
        forest2.addWay("W", crossroad);
        forest2.addWay("N", forest1);
        forest2.addWay("E", forest3);
        forest3.addWay("W", forest2);
        forest3.addWay("N", coast);
        forest4.addWay("N", crossroad);
        forest4.addWay("E", bridge);
        bridge.addWay("W", forest4);
        bridge.addWay("S", underBridge);
        underBridge.addWay("N", bridge);
        bridge.addWay("E", inFrontOfStore);
        inFrontOfStore.addWay("W", bridge);
        inFrontOfStore.addWay("N", store);
        store.addWay("S", inFrontOfStore);
        inFrontOfStore.addWay("S", forest5);
        forest5.addWay("N", inFrontOfStore);
        forest5.addWay("W", caveEntry);
        caveEntry.addWay("E", forest5);
        
        //Náhodné umístění tajné lokace
        Random rand = new Random();
        switch(rand.nextInt(4)){
            case 0: 
                forest1.addWay("N", secretPlace);
                secretPlacePosition = "north";
                secretPlace.addWay("S", forest1);
                secretPlace.setPosTop(30);
                secretPlace.setPosLeft(165);
                break;
            case 1:
                forest3.addWay("E", secretPlace);
                secretPlacePosition = "east";
                secretPlace.addWay("W", forest3);
                secretPlace.setPosTop(170);
                secretPlace.setPosLeft(320);
                break;
            case 2:
                forest4.addWay("W", secretPlace);
                secretPlacePosition = "west";
                secretPlace.addWay("E", forest4);
                secretPlace.setPosTop(240);
                secretPlace.setPosLeft(10);
                break;
            default:
                forest5.addWay("S", secretPlace);
                secretPlacePosition = "south";
                secretPlace.addWay("N", forest5);
                secretPlace.setPosTop(360);
                secretPlace.setPosLeft(250);
                break;
        }
    }
    
    /**
     * Metoda vytvoří postavy a umístí je na příslušné lokace ve hře.
     */
    private void createCharacters(){
        //Vytvoříme postavy
        grandma = new Character(game, "grandma", true, false);
        cottage.addCharacter(grandma);
        grandma.addFirstSpeech("Hi grandson! What can I help you with?");
        grandma.addNoItemAnswer("I give you habitation, food, love.. What else"
                + " do you want from me?!");
        grandma.addExchange("meat", "You want me to do a sandwich out of this?"
                + " Are you kidding your old grandma?", "Great, here's your "
                + "sandwich!", "sandwich");
        grandma.addGoodBye("Have fun and see ya!");
        grandma.addPuzzles("2+2?", "7-4?", "8+5?", "10*4?", "15/3?");
        grandma.addAnswersForPuzzles("4", "3", "13", "40", "5");
        
        fisherman = new Character(game, "fisherman", true, false);
        coast.addCharacter(fisherman);
        fisherman.addFirstSpeech("Oh, hey! I'm the old fisherman. What can I "
                + "help you with?", "Hey, what's up?");
        fisherman.addNoExchangeAnswer("I don't want anything. Keep it and "
                + "paddle your own canoe");
        fisherman.addGoodBye("Have a good day, strange man!");
        
        shepherd = new Character(game, "shepherd", true, false);
        shepherd.addValidLocations(crossroad, bridge, inFrontOfStore);
        shepherd.returnShuffleLocation().addCharacter(shepherd);
        shepherd.addFirstSpeech("Hey man, I'm local shepherd. How can I help "
                + "you?", "Hey, what's going on?");
        shepherd.addNoExchangeAnswer("I have nothing to give you for it, so "
                + "keep it. But thanks!");
        shepherd.addGoodBye("See you!");
        
        shopgirl = new Character(game, "shopgirl", true, true);
        store.addCharacter(shopgirl);
        shopgirl.addFirstSpeech("Welcome to Kwik-E-Mart, what could I do for "
                + "you?");
        shopgirl.addExchange("money", "You can pay me only with money, sorry",
                "Thank you, here's your meat! Come again!", "meat");
        shopgirl.addGoodBye("Thank you, come again!");
        shopgirl.addNoItemAnswer("You're in a store, I can't give you anything "
                + "for free..");
        
        //Náhodně vybereme, kdo bude vědět, kde je tajné místo
        ArrayList<Character> charsList = new ArrayList<>();
        charsList.add(fisherman);
        charsList.add(shepherd);
        charsList.add(shopgirl);
        Collections.shuffle(charsList);
        
        charSecretPlace = charsList.get(0);
        
        grandma.addSecretPlace(charsList.get(0), secretPlacePosition);
        fisherman.addSecretPlace(charsList.get(0), secretPlacePosition);
        shepherd.addSecretPlace(charsList.get(0), secretPlacePosition);
        shopgirl.addSecretPlace(charsList.get(0), secretPlacePosition);
        
        //Přidáme rady
        grandma.addAdvice("If you want to kill the dragon, you need to get "
                + "a mystic sword. I don't know where it is, but the old "
                + "fisherman should know that. Go to the coast and ask him. "
                + "It's in the north-east.", "You want a sandwich? I'll make "
                + "you one, but I don't have a meat. If you'll bring me some, "
                + "I'll do it. Just grab money here and go to the store or find"
                + " another way, how you get meat..", "What are you waiting "
                + "for? You have the sword, so go quickly to the cave and kill "
                + "the dragon!!");
        fisherman.addAdvice("You're trying to get a sword don't you? Ok, so "
                + "the sword is deep in the sea. But you can't get there "
                + "without oxygen bomb. I had one, but the imp from under the "
                + "bridge stole it from me. \nBut he won't give you the bomb "
                + "that easy. He is a bad smoker, so I think, if you bring him "
                + "a joint, he will give you the oxygen bomb. First you have to"
                + " find a secret marijuana garden, where you can get weed for "
                + "him. \nI'm pretty sure, that somenone knows where it is, so "
                + "try to ask people, maybe they'll tell you more.", "Did you "
                + "tried to cathc a squirrel? You need to have a can, then type"
                + " catch squirrel, while you'll be in the forest and maybe, "
                + "you'll be lucky.", "What are you waiting for? Just go and "
                + "kill the dragon!");
        shepherd.addAdvice("The fisherman is in the coast. It's north-east", 
                "Did you tried to cathc a squirrel? You need to have a can, "
                + "then type catch squirrel, while you'll be in the forest and "
                + "maybe, you'll be lucky.", "Kill the dragon!!!");
        shopgirl.addAdvice("If you're looking for the fisherman, he's in the "
                + "north-east, by the seacoast", "Just give me money "
                + "(command use money) and I'll give you a meat", "Come on! "
                + "You're the choosen one! Go and kill the dragon!!");
        
        //Vytvoříme i ostatní postavy
        pothead = new Character(game, "pothead", false, false);
        secretPlace.addCharacter(pothead);
        pothead.addFirstSpeech("Hey bro! I think you're looking for a way to "
                + "get this weed, right? Huh, I can cut it and roll it for "
                + "you, if you want. But first you have to bring me a sandwich."
                + " I'l be your debtor bro! I badly need some munchies!!");
        pothead.addAdvice("Bring me sandwich and I'll prepare a joint for you."
                + " Can you do this for me?");
        pothead.addExchange("sandwich", "Man, I can't eat this kind of thing..",
                "You're the best bro!! Here's your joint!", "weed");
        pothead.addGoodBye("Peace bro!");
        pothead.addNoItemAnswer("I don't have anything bro");
        
        imp = new Character(game, "imp", false, true);
        underBridge.addCharacter(imp);
        imp.addFirstSpeech("What are you looking for here at my home?!!");
        imp.addAdvice("F*ck you!");
        imp.addExchange("weed", "What should I do with that? Get the f*ck out!",
                "Wow, a joint! Smoke 'em up Johnny!", "oxygen");
        imp.addGoodBye("Go to hel!!");
        imp.addNoItemAnswer("How dare you?! Get the f*ck of my home!!");
        
        homeless = new Character(game,"homeless",false, true);
        homeless.addValidLocations(forest1, forest2, forest3, forest4, forest5);
        homeless.returnShuffleLocation().addCharacter(homeless);
        homelessOnMap = true;
        homeless.addFirstSpeech("Huh, give me your things kid!");
        
        shark = new Character(game, "shark", false, false);
        shark.addValidLocations(sea1, sea2);
        shark.returnShuffleLocation().addCharacter(shark);
        sharkOnMap = true;
        
        dragon = new Character(game, "dragon", false, false);
        caveEntry.addCharacter(dragon);
        
    }
    
    /**
     * Metoda je spuštěna při každém tahu hráče (při zavolání příkazu go) a 
     * přemístí všechny pohyblivé postavy
     */
    public void updateCharacters(){
        shepherd.getLocation().removeCharacter(shepherd);
        shepherd.returnShuffleLocation().addCharacter(shepherd);
        
        Random rand = new Random();
        if(homelessOnMap){
            homeless.getLocation().removeCharacter(homeless);
            homelessOnMap = false;
        }
        if(rand.nextBoolean()){
            homeless.returnShuffleLocation().addCharacter(homeless);
            homelessOnMap = true;
        }
        
        if(game.getLevel() > 6){
            if(sharkOnMap){
                shark.getLocation().removeCharacter(shark);
                sharkOnMap = false;
            }
            if (rand.nextBoolean()){
                shark.returnShuffleLocation().addCharacter(shark);
                sharkOnMap = true;
            }
        }
    }
    
    /**
     * Metoda vytvoří předměty a umístí je na příslušné lokace ve hře.
     */
    private void addItems(){
        //Vytvoříme všechny předměty
        pole = new Item("pole");
        plank = new Item("plank");
        can = new Item("can");
        rum = new Item("rum");
        rum1 = new Item("rum");
        rum2 = new Item("rum");
        key = new Item("key");
        key1 = new Item("key");
        scissors = new Item("scissors");
        wood = new Item("wood");
        money = new Item("money");
        meat = new Item("meat");
        sandwich = new Item("sandwich");
        weed = new Item("weed");
        weed1 = new Item("weed");
        sword = new Item("sword");
        oxygen = new Item("oxygen");
        
        //A truhly, ke kterým přiřadíme klíč a uložíme je do daných lokací
        chest = new Item("chest");
        chest.addKey(key);
        crossroad.addItem(chest);
        chest1 = new Item("chest");
        chest1.addKey(key1);
        coast.addItem(chest1);
        
        //Necháme náhodně vybrat ze tří scénářů rozmístění předmětů
        Random rand = new Random();
        switch(rand.nextInt(3)){
            case 0:
                chest1.addItemToChest(scissors);
                chest.addItemToChest(rum);
                fisherman.addItem(rum1, "I got a rum from by birthday, but I "
                        + "don't drink, so you can take it.", "No, i don't have"
                                + "anything else.");
                inFrontOfStore.addItem(rum2);
                crossroad.addItem(can);
                shepherd.addItem(key1, "I found a key on the ground. If you "
                        + "figure out what the key opens, it's yours.", "No I "
                                + "can't give you anything more..");
                coast.addItem(key);
                chest.addItemToChest(pole);
                forest1.addItem(plank);
                break;
            case 1:
                shepherd.addItem(scissors, "Oh look, I found scissors in my "
                        + "pocket. Here, take it.", "I'm just a poor shepherd, "
                                + "what else do you want?");
                chest1.addItemToChest(rum);
                chest.addItemToChest(rum1);
                bridge.addItem(rum2);
                forest3.addItem(can);
                fisherman.addItem(key, "Here, take the key I found yesterday. "
                        + "I still don't know what it opens, so now it's your "
                        + "job to find out.", "No, i don't have anything "
                                + "else.");
                forest5.addItem(key1);
                chest1.addItemToChest(plank);
                crossroad.addItem(pole);
                break;
            default:
                chest.addItemToChest(scissors);
                fisherman.addItem(rum, "I got a rum from by birthday, but I "
                        + "don't drink, so you can take it.", "No, i don't have"
                                + "anything else.");
                chest1.addItemToChest(rum);
                forest4.addItem(rum2);
                inFrontOfStore.addItem(can);
                secretPlace.addItem(key);
                forest3.addItem(key1);
                crossroad.addItem(plank);
                forest1.addItem(pole);
                break;                                   
        }
        
        secretPlace.addItem(weed1);
        sea1.addItem(sword);
        
        unpositionedItems = new HashMap<>();
        unpositionedItems.put(wood.getName(), wood);
        unpositionedItems.put(meat.getName(), meat);
        unpositionedItems.put(sandwich.getName(), sandwich);
        unpositionedItems.put(weed.getName(), weed);
        unpositionedItems.put(oxygen.getName(), oxygen);
    }
    
    /**
     * Metoda je spuštěna při každém tahu hráče (při zavolání příkazu go) a 
     * přemístí pohyblivé předměty
     */
    public void updateItems(){      
        // V "levelu" 7 je možnost dostat se do vody. Proto se meč přemisťuje
        // až v tomto momentu.
        if (game.getLevel() == 7){
            Random rand = new Random();
            sword.getLocation().removeItem(sword);
            if (rand.nextInt(2) == 0){
                sea1.addItem(sword);
            }
            else{
                sea2.addItem(sword);
            }
        }
        if(game.getLevel() == 5 && !cottage.hasItem(money.getName())){
            cottage.addItem(money);
        }
        if(!unpositionedItems.containsValue(meat)){
            unpositionedItems.put(meat.getName(), meat);
        }
        if(!unpositionedItems.containsValue(weed)){
            unpositionedItems.put(weed.getName(), weed);
        }
        if(!unpositionedItems.containsValue(sandwich)){
            unpositionedItems.put(sandwich.getName(), sandwich);
        }
    }
    
    /**
     * Tato metoda vrací předmět neumístěný na mapě podle zadaného názvu
     * @param item název předmětu
     * @return předmět samotný
     */
    public Item getUnpositionedItem(String item){
        return unpositionedItems.remove(item);
    }
    
    /**
     * Metoda vrátí aktuální lokaci, ve které se hráč nachází.
     * @return aktuální lokace
     */
    public Location getCurrentLocation(){
        return currentLocation;
    }
    
    /**
     * Metoda vrátí přechozí lokaci, na které hráč byl v předchozím tahu.
     * @return předchozí lokace
     */
    public Location getPreviousLocation(){
        return previousLocation;
    }
    
    /**
     * Nastaví aktuální lokaci na zadanou a zároveň uloží přechozí lokaci do 
     * proměnné přechozí lokace (možné získat pomocí metody getPreviousLocation)
     * Využívá se zejména k přechodu mezi Lokacemi.
     * @param location Lokace, na kterou má hráč přejít
     */
    public void setCurrentLocation(Location location){
        this.previousLocation = this.currentLocation;
        this.currentLocation = location;
    }
    
    /**
     * Metoda vrací postavu, která ví, kde je skryté místo
     * @return postava, která ví, kde je skryté místo
     */
    public Character returnCharSecretPlace(){
        return charSecretPlace;
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
