/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package logic;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;



/*******************************************************************************
 * The testclass {@code ItemTest} tests the class
 * {@link ItemTest}.
 *
 * @author  author name
 * @version 0.00.0000 — 20yy-mm-dd
 */
public class ItemTest
{
    //== CONSTANT CLASS ATTRIBUTES =============================================
    //== VARIABLE CLASS ATTRIBUTES =============================================

    private Item item;
    private Item item1;
    
    private Location loc;
    
    private Item chest;
    private Item key;

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
        item = new Item("pole");
        item1 = new Item("item");
        
        loc = new Location("A1", "crossroad");
        
        chest = new Item("chest");
        key = new Item("key");
        chest.addKey(key);
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
    public void itemTest(){
        assertEquals("pole",item.getName());
        assertEquals("unknown",item1.getName());
        assertEquals("Some kind of pole",item.getDescription());
        assertEquals("Item doesn't exist",item1.getDescription());
        
        item.setLocation(loc);
        assertEquals(loc, item.getLocation());
        
        assertFalse(chest.chestOpened());
        
        chest.setLocation(loc);
        chest.addItemToChest(item1);
        assertEquals(loc,item1.getLocation());
        
        assertTrue(chest.openChest(key));
        assertTrue(chest.chestOpened());
    }
}
