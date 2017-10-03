/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package logic;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;



/*******************************************************************************
 * The testclass {@code LocationTest} tests the class
 * {@link LocationTest}.
 *
 * @author  author name
 * @version 0.00.0000 — 20yy-mm-dd
 */
public class LocationTest
{
    //== CONSTANT CLASS ATTRIBUTES =============================================
    //== VARIABLE CLASS ATTRIBUTES =============================================
    private Location crossroad;
    private Location crossroad2;
    private Location crossroad3;
    private Game game;
    private Item item;
    private Character character;


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
        crossroad = new Location("A1","crossroad");
        crossroad2 = new Location("A2","crossroad");
        crossroad3 = new Location("A1","crossroad");
        
        game = new Game();
        
        item = new Item("item");
        character = new Character(game,"character",false,false);
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
    public void locationTest(){
        assertEquals("crossroad",crossroad.getName());
        assertEquals("A1",crossroad.getID());
        assertFalse(crossroad.equals(crossroad2));
        assertTrue(crossroad.equals(crossroad3));
        
        crossroad.addItem(item);
        assertTrue(crossroad.hasItem(item.getName()));
        assertEquals(item,crossroad.returnItem(item.getName()));
        assertTrue(crossroad.hasItem(item.getName()));
        assertEquals(item,crossroad.grabItem(item.getName()));
        assertFalse(crossroad.hasItem(item.getName()));
        
        crossroad.addCharacter(character);
        assertTrue(crossroad.getHasCharacter());
        assertEquals(character, crossroad.returnCharacter());
        assertTrue(crossroad.getHasCharacter());
        crossroad.removeCharacter(character);
        assertFalse(crossroad.getHasCharacter());
    }
}
