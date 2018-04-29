package edu.T10;
import edu.T10.Controller.TestGame;
import edu.T10.Model.*;
import edu.T10.Model.Board.*;
import edu.T10.Model.Deck.TestGameDeck;
import edu.T10.Model.Deck.TestPlayerDeck;
import edu.T10.Model.Deck.TestCard;
import edu.T10.Model.Deck.TestPlayerDeck;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import junit.framework.JUnit4TestAdapter;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestCard.class,
    TestDice.class,
    TestGame.class,
    TestGameDeck.class,
    TestPlayerDeck.class,
    TestInvasionResult.class,
    TestPlayer.class,
    TestBoard.class,
    TestContinent.class,
    TestTerritory.class,
    TestServer.class
})

public class AllTests {

    public static void main (String[] args)
    {
       junit.textui.TestRunner.run (suite());
    }

    public static junit.framework.Test suite()
    {
       return new JUnit4TestAdapter(AllTests.class);
    }
}
