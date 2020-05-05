package it.polimi.ingsw.ps51.controller.gods;

import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Player;
import it.polimi.ingsw.ps51.model.gods.Card;
import it.polimi.ingsw.ps51.model.gods.CardFactory;
import it.polimi.ingsw.ps51.model.gods.Gods;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GodsControllerFactoryTest {

    GodController controller;
    Card card;
    Player player;
    Map map;

    @Before
    public void setUp() throws Exception {
        player = new Player("Player");
        map = new Map();
    }

    @After
    public void tearDown() throws Exception {
        controller = null;
        card = null;
        player = null;
        map = null;
    }

    @Test
    public void getControllerTest_Demeter_DemeterControllerReturned() {
        card = CardFactory.getCard(Gods.DEMETER);
        controller = GodsControllerFactory.getController(Gods.DEMETER, player, map, card);

        assertTrue(controller instanceof DemeterController);
    }

    @Test
    public void getControllerTest_Prometheus_PrometheusControllerReturned() {
        card = CardFactory.getCard(Gods.PROMETHEUS);
        controller = GodsControllerFactory.getController(Gods.PROMETHEUS, player, map, card);

        assertTrue(controller instanceof PrometheusController);
    }

    @Test
    public void getControllerTest_Atlas_NormalGodsControllerReturned() {
        card = CardFactory.getCard(Gods.ATLAS);
        controller = GodsControllerFactory.getController(Gods.ATLAS, player, map, card);

        assertTrue(controller instanceof NormalGodsController);
    }

    @Test
    public void getControllerTest_Athena_NormalGodsControllerReturned() {
        card = CardFactory.getCard(Gods.ATHENA);
        controller = GodsControllerFactory.getController(Gods.ATHENA, player, map, card);

        assertTrue(controller instanceof NormalGodsController);
    }

    @Test
    public void getControllerTest_Apollo_NormalGodsControllerReturned() {
        card = CardFactory.getCard(Gods.APOLLO);
        controller = GodsControllerFactory.getController(Gods.APOLLO, player, map, card);

        assertTrue(controller instanceof NormalGodsController);
    }

    @Test
    public void getControllerTest_Artemis_ArtemisControllerReturned() {
        card = CardFactory.getCard(Gods.ARTEMIS);
        controller = GodsControllerFactory.getController(Gods.ARTEMIS, player, map, card);

        assertTrue(controller instanceof ArtemisController);
    }

    @Test
    public void getControllerTest_Hephaestus_NormalGodsControllerReturned() {
        card = CardFactory.getCard(Gods.HEPHAESTUS);
        controller = GodsControllerFactory.getController(Gods.HEPHAESTUS, player, map, card);

        assertTrue(controller instanceof NormalGodsController);
    }

    @Test
    public void getControllerTest_Minotaur_NormalGodsControllerReturned() {
        card = CardFactory.getCard(Gods.MINOTAUR);
        controller = GodsControllerFactory.getController(Gods.MINOTAUR, player, map, card);

        assertTrue(controller instanceof NormalGodsController);
    }

    @Test
    public void getControllerTest_Pan_NormalGodsControllerReturned() {
        card = CardFactory.getCard(Gods.PAN);
        controller = GodsControllerFactory.getController(Gods.PAN, player, map, card);

        assertTrue(controller instanceof NormalGodsController);
    }
}