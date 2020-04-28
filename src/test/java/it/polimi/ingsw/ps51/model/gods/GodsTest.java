package it.polimi.ingsw.ps51.model.gods;

import org.junit.Test;

import static org.junit.Assert.*;

public class GodsTest {

    @Test
    public void isOpponentTurnEffectTest_Athena_ReturnTrue() {

        assertTrue(Gods.isOpponentTurnEffect(new Athena()));
    }

    @Test
    public void isOpponentTurnEffectTest_Demeter_ReturnFalse() {

        assertFalse(Gods.isOpponentTurnEffect(new Demeter()));
    }

    @Test
    public void getGodFromStringTest_CorrectStringFormat(){
        assertEquals(Gods.ARTEMIS, Gods.getGodFromString("Artemis"));
    }

    @Test
    public void getGodFromStringTest_CorrectNameWrongFormat_ReturnCorrectGod(){
        assertEquals(Gods.DEMETER, Gods.getGodFromString("demeter"));
    }

    @Test
    public void getGodFromStringTest_InexistentName_ReturnNull(){
        assertNull(Gods.getGodFromString("notExistent"));
    }
}