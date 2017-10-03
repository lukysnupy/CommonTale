/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package logic;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import logic.commands.AvailableCommands;


import static org.junit.Assert.*;



/*******************************************************************************
 * The testclass {@code GameTest} tests the class
 * {@link GameTest}.
 *
 * @author  author name
 * @version 0.00.0000 — 20yy-mm-dd
 */
public class GameTest
{
    //== CONSTANT CLASS ATTRIBUTES =============================================
    //== VARIABLE CLASS ATTRIBUTES =============================================
    private Game game;
    private GamePlan gamePlan;
    private Bag bag;
    private AvailableCommands commands;


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
        commands = new AvailableCommands(game, gamePlan, bag);
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
    public void gameTest(){
        assertEquals(1,game.getLevel());
        assertFalse(game.isOver());
        Location loc = gamePlan.getCurrentLocation();
        game.compileCommand("go south");
        assertEquals(loc, gamePlan.getPreviousLocation());
        assertFalse(game.isOver());
        game.increaseLevel();
        assertEquals(2,game.getLevel());
    }

}
