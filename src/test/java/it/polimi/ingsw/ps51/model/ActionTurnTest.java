package it.polimi.ingsw.ps51.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class ActionTurnTest {

    private ActionTurn actionTurn = null;

    @Before
    public void setUp() {
        this.actionTurn = new ActionTurn();
    }

    @After
    public void tearDown() {
        this.actionTurn = null;
    }

    @Test
    public void setNicknameOfPlayerTest() {
        String nick = "name";
        this.actionTurn.setNicknameOfPlayer(nick);
        Assert.assertEquals(nick, this.actionTurn.getNickname());
    }

    @Test
    public void getNicknameTest() {
        Assert.assertNull(this.actionTurn.getNickname());
    }

    @Test
    public void setActionTest() {
        Action action = new Build( null, null, false);
        this.actionTurn.setAction(action);
        Assert.assertEquals(action, this.actionTurn.getAction());
    }

    @Test
    public void getActionTest() {
        Assert.assertNull(this.actionTurn.getAction());
    }

    @Test
    public void setNumberOfTurnTest() {
        this.actionTurn.setNumberOfTurn(3);
        Assert.assertEquals(java.util.Optional.of(3), java.util.Optional.ofNullable(this.actionTurn.getNumberOfTurn()));
    }

    @Test
    public void getNumberOfTurnTest() {
        Assert.assertEquals(java.util.Optional.of(0), java.util.Optional.ofNullable(this.actionTurn.getNumberOfTurn()));
    }

}