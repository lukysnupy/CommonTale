/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package logic;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;



/*******************************************************************************
 * The testclass {@code CharacterTest} tests the class
 * {@link CharacterTest}.
 *
 * @author  author name
 * @version 0.00.0000 — 20yy-mm-dd
 */
public class CharacterTest
{
    //== CONSTANT CLASS ATTRIBUTES =============================================
    //== VARIABLE CLASS ATTRIBUTES =============================================
    private Game game;
    private Character char1;
    private Character char2;
    private Character char3;
    private Character char4;
    private Item item;
    private Location loc;


    //##########################################################################
    //== STATIC INITIALIZER (CLASS CONSTRUCTOR) ================================
    //== CLASS GETTERS AND SETTERS =============================================
    //== OTHER NON-PRIVATE CLASS METHODS =======================================
    //== PRIVATE AND AUXILIARY CLASS METHODS ===================================



    //##########################################################################
    //== CONSTANT INSTANCE ATTRIBUTES ==========================================
    //== VARIABLE INSTANCE ATTRIBUTES ==========================================



    //##########################################################################
    //== CONSTRUCTORS AND FACTORY METHODS ======================================
    //-- Test class manages with empty default constructor ---------------------
    //== PREPARATION AND CLEAN UP OF THE TEST FIXTURE ==========================

    /***************************************************************************
     * Prepare the tested instances and perform the the actions,
     * which should be performed before any test.
     */
    @Before
    public void setUp()
    {
        game = new Game();
        char1 = new Character(game,"man",true,false);
        char2 = new Character(game,"woman",false,false);
        char3 = new Character(game,"fakeman",false,false);
        char4 = new Character(game,"man",false,false);
        item = new Item("pole");
        loc = new Location("A1","crossroad");
        
        char1.addFirstSpeech("ahoj", "cau");
        char2.addFirstSpeech("ahoj");
        char3.addFirstSpeech("ahoj", "cau");
        
        char1.addItem(item,"cau","ahoj");
        char2.addNoItemAnswer("ahoj");
        
        char1.addSecretPlace(char1, "north");
        
        char1.addAdvice("1","3","4");
        char2.addAdvice("1");
        char3.addAdvice("1","3","4");
        
        char1.addExchange("pole","ahoj", "cau", "plank");
        char2.addNoExchangeAnswer("ahoj");
        
        char1.setLocation(loc);
    }


    /***************************************************************************
     * Cleans up and perform the the actions,
     * which should be performed after each test.
     */
    @After
    public void tearDown()
    {
    }



    //== ABSTRACT METHODS ======================================================
    //== INSTANCE GETTERS AND SETTERS ==========================================
    //== OTHER NON-PRIVATE INSTANCE METHODS ====================================
    //== PRIVATE AND AUXILIARY INSTANCE METHODS ================================



    //##########################################################################
    //== NESTED DATA TYPES =====================================================



    //##########################################################################
    //== TESTS PROPER ==========================================================
    
    @Test
    public void characterTest(){
        assertEquals("ahoj",char1.returnFirstSpeech());
        assertEquals("cau",char1.returnFirstSpeech());
        assertEquals("ahoj",char2.returnFirstSpeech());
        assertEquals("ahoj",char2.returnFirstSpeech());
        assertEquals("ahoj",char3.returnFirstSpeech());
        assertEquals("ahoj",char3.returnFirstSpeech());
        
        assertEquals("cau",char1.returnItemSpeech());
        assertTrue(char1.getCanGiveItem());
        assertEquals(item,char1.giveItem());
        assertEquals("ahoj",char1.returnItemSpeech());
        assertEquals("ahoj",char2.returnItemSpeech());
        assertFalse(char2.getCanGiveItem());
        
        game.setLevel(2);
        assertEquals("1",char1.returnAdvice());
        assertEquals("1",char2.returnAdvice());
        assertEquals("1",char3.returnAdvice());
        game.setLevel(3);
        assertEquals("You're looking for the secret place? You need to go north of the northernmost place",
        char1.returnAdvice());
        assertEquals("1",char2.returnAdvice());
        assertEquals("1",char3.returnAdvice());
        game.setLevel(5);
        assertEquals("3",char1.returnAdvice());
        assertEquals("1",char2.returnAdvice());
        assertEquals("1",char3.returnAdvice());
        game.setLevel(8);
        assertEquals("4",char1.returnAdvice());
        assertEquals("1",char2.returnAdvice());
        assertEquals("1",char3.returnAdvice());
        
        assertTrue(char1.getWantItem());
        assertTrue(char1.neededItem("pole"));
        assertFalse(char1.neededItem("plank"));
        assertEquals("plank",char1.returnExchangedItem());
        assertEquals("ahoj",char1.obtainItem("plank"));
        assertEquals("cau",char1.obtainItem("pole"));
        
        assertEquals("ahoj",char2.returnDontWantItem());
    }

}
