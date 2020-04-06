package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PanTest {
    Map map;
    Pan card = new Pan();
    Worker worker;

    @Before
    public void setUp() throws Exception {
        map = new Map();
        worker = new Worker("Player");
    }

    @After
    public void tearDown() throws Exception {
        map = null;
        worker = null;
    }

    @Test
    public void moveTest_FromX2Y3ToX2Y2_() {
    }
}