package it.polimi.ingsw.ps51.model.gods;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardFactoryTest {

    Card card;

    @After
    public void tearDown() throws Exception {
        card = null;
    }

    @Test
    public void getCardTest_GetApollo_ApolloReturned() {
        card = CardFactory.getCard(Gods.APOLLO);

        assertTrue(card instanceof Apollo);
    }

    @Test
    public void getCardTest_GetAtlas_AtlasReturned() {
        card = CardFactory.getCard(Gods.ATLAS);

        assertTrue(card instanceof Atlas);
    }

    @Test
    public void getCardTest_GetAthena_AthenaReturned() {
        card = CardFactory.getCard(Gods.ATHENA);

        assertTrue(card instanceof Athena);
    }

    @Test
    public void getCardTest_GetPrometheus_PrometheusReturned() {
        card = CardFactory.getCard(Gods.PROMETHEUS);

        assertTrue(card instanceof Prometheus);
    }

    @Test
    public void getCardTest_GetDemeter_DemeterReturned() {
        card = CardFactory.getCard(Gods.DEMETER);

        assertTrue(card instanceof Demeter);
    }

    @Test
    public void getCardTest_GetPan_PanReturned() {
        card = CardFactory.getCard(Gods.PAN);

        assertTrue(card instanceof Pan);
    }

    @Test
    public void getCardTest_GetArtemis_ArtemisReturned() {
        card = CardFactory.getCard(Gods.ARTEMIS);

        assertTrue(card instanceof Artemis);
    }

    @Test
    public void getCardTest_GetMinotaur_MinotaurReturned() {
        card = CardFactory.getCard(Gods.MINOTAUR);

        assertTrue(card instanceof Minotaur);
    }

    @Test
    public void getCardTest_GetHephaestus_HephaestusReturned() {
        card = CardFactory.getCard(Gods.HEPHAESTUS);

        assertTrue(card instanceof Hephaestus);
    }

}