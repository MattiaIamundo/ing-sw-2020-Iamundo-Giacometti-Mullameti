package it.polimi.ingsw.ps51.model.gods.opponent_move_manager;

import it.polimi.ingsw.ps51.model.gods.Gods;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OpponentGodsFactoryTest {

    OpponentTurnGodsManager manager = null;
    OpponentGodsFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new OpponentGodsFactory();
    }

    @After
    public void tearDown() throws Exception {
        factory = null;
    }

    @Test
    public void getGodTest_Athena_ReturnAValidManager() {
        manager = factory.getGod(Gods.ATHENA);

        Assert.assertNotNull(manager);
    }

}