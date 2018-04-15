package edu.T10;
import edu.T10.Model.*;
import edu.T10.Model.Board.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import junit.framework.JUnit4TestAdapter;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestCard.class,
    TestDeck.class,
    TestDice.class,
    TestGame.class,
    TestInvasionResult.class,
    TestPlayer.class,
    TestBoard.class,
    TestContinent.class,
    TestTerritory.class
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
