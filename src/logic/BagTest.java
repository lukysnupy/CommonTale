/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package logic;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;



/*******************************************************************************
 * The testclass {@code BagTest} tests the class
 * {@link BagTest}.
 *
 * @author  author name
 * @version 0.00.0000 — 20yy-mm-dd
 */
public class BagTest
{
    //== CONSTANT CLASS ATTRIBUTES =============================================
    //== VARIABLE CLASS ATTRIBUTES =============================================
    private Game game;
    private GamePlan gamePlan;
    private Bag bag;
    private Item item;
    private Item item1;
    private Item item2;


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
        gamePlan = new GamePlan(game);
        bag = new Bag(gamePlan);
        item = new Item("pole");
        item1 = new Item("plank");
        item2 = new Item("meat");
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
    public void bagTest(){
        assertTrue(bag.getLocked());
        bag.unlockBag();
        assertFalse(bag.getLocked());
        
        assertEquals(0,bag.bagContentCount());
        bag.addItem(item);
        assertEquals(1,bag.bagContentCount());
        
        bag.throwItem(item.getName());
        assertEquals(0,bag.bagContentCount());
        bag.addItem(item.getName());
        assertEquals(1,bag.bagContentCount());
        
        bag.addItem(item1);
        assertEquals(item,bag.getItem(item.getName()));
        assertEquals(null,bag.getItem(item.getName()));
        assertEquals(item1,bag.returnItem(item1.getName()));
        assertEquals(item1,bag.getItem(item1.getName()));
        
        bag.addItem(item);
        assertEquals(1,bag.bagContentCount());
        assertTrue(bag.containsItem(item.getName()));
        bag.removeItems();
        assertEquals(0,bag.bagContentCount());
        
        bag.addItem(item);
        bag.addItem(item1);
        assertEquals("Your bag is full! You need to throw something away.",bag.addItem(item2));
    }
}
