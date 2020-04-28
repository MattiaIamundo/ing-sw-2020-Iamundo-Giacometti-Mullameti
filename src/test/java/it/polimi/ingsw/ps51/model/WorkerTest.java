package it.polimi.ingsw.ps51.model;

import it.polimi.ingsw.ps51.model.gods.Athena;
import it.polimi.ingsw.ps51.model.gods.Card;
import it.polimi.ingsw.ps51.model.gods.Demeter;
import it.polimi.ingsw.ps51.model.gods.Gods;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WorkerTest {

    private Worker worker = null;
    @Before
    public void setUp()  {
        this.worker=new Worker("Nickname");
    }

    @After
    public void tearDown()  {
        this.worker=null;
    }

    @Test
    public void getNamePlayerTest() {
        Assert.assertEquals("Nickname" ,this.worker.getNamePlayer());
    }

    @Test
    public void setPositionTest() {
        Square position = new Square(null);
        this.worker.setPosition(position);
        Assert.assertEquals(position, this.worker.getPosition());
    }

    @Test
    public void getPositionTest() {
        Assert.assertNull(this.worker.getPosition());
    }

    @Test
    public void getInWinningConditionTest() {
        Assert.assertFalse(this.worker.isInWinningCondition());
    }

    @Test
    public void setInWinningConditionTest() {
        this.worker.setInWinningCondition(true);
        Assert.assertTrue(this.worker.isInWinningCondition());
    }

    @Test
    public void removeGodTest_GodInActiveGodsList_GodRemoved(){
        worker.updateGods(Gods.ATHENA);
        Athena athena = new Athena();
        worker.removeGod(athena);

        Assert.assertEquals(0, worker.getActiveGods().size());
    }

    @Test
    public void removeGodtest_GodNotInActiveGodsList_NoChange(){
        worker.updateGods(Gods.ATHENA);
        Card demeter = new Demeter();
        worker.removeGod(demeter);

        Assert.assertEquals(Gods.ATHENA, worker.getActiveGods().get(0));
    }

    @Test
    public void equalTest_SameObject_Returntrue(){
        Assert.assertTrue(worker.equals(worker));
    }

    @Test
    public void equalTest_NullPointer_returnFalse(){
        Assert.assertFalse(worker.equals(null));
    }

    @Test
    public void equalTest_EqualObject_ReturnTrue(){
        Square square = new Square(new Coordinates(2,2));
        worker.setPosition(square);
        Worker worker2 = new Worker("Nickname");
        worker2.setPosition(square);

        Assert.assertTrue(worker.equals(worker2));
    }

    @Test
    public void cloneTest() {
        try {
            Square square = new Square(new Coordinates(3,3));
            worker.setPosition(square);
            Worker clonedWorker = (Worker) worker.clone();
            Assert.assertTrue(worker.equals(clonedWorker));

            worker.updateGods(Gods.ATHENA);
            Assert.assertNotEquals(worker.getActiveGods(), clonedWorker.getActiveGods());

            worker.setInWinningCondition(true);
            Assert.assertNotEquals(worker.isInWinningCondition(), clonedWorker.isInWinningCondition());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }


    }
}