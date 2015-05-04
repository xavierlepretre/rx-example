package com.melotic.api.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class MarketMapTest
{
    private ObjectMapper mapper;

    @Before public void setUp()
    {
        this.mapper = new ObjectMapper();
    }

    @Test public void testCanPutNull()
    {
        new MarketMap().put(new MarketId("id1"), null);
    }

    @Test public void testPutPopulatesMarket()
    {
        MarketMap marketMap = new MarketMap();
        marketMap.put(new MarketId("id1"), new Market());

        assertThat(marketMap.get(new MarketId("id1")).id).isEqualTo(new MarketId("id1"));
    }

    @Test public void testPutAllPopulatesMarket()
    {
        MarketMap marketMap = new MarketMap();
        Map<MarketId, Market> simpleMap = new HashMap<>();
        simpleMap.put(new MarketId("id1"), new Market());
        simpleMap.put(new MarketId("id2"), new Market());
        marketMap.putAll(simpleMap);

        assertThat(marketMap.get(new MarketId("id1")).id).isEqualTo(new MarketId("id1"));
        assertThat(marketMap.get(new MarketId("id2")).id).isEqualTo(new MarketId("id2"));
    }

    @Test public void testDeserialise1PopulatesMarket() throws IOException
    {
        MarketMap marketMap = mapper.readValue(getClass().getResourceAsStream("market_map1.json"), MarketMap.class);

        assertThat(marketMap).isNotNull();
        assertThat(marketMap.size()).isEqualTo(1);
        Map.Entry<MarketId, Market> pair = marketMap.entrySet().iterator().next();
        assertThat(pair.getKey()).isEqualTo(new MarketId("market-1"));
        assertThat(pair.getValue().id).isEqualTo(new MarketId("market-1"));
    }

    @Test public void testDeserialise2PopulatesMarket() throws IOException
    {
        MarketMap marketMap = mapper.readValue(getClass().getResourceAsStream("market_map2.json"), MarketMap.class);

        assertThat(marketMap).isNotNull();
        assertThat(marketMap.size()).isEqualTo(2);
        Map.Entry<MarketId, Market> pair1 = marketMap.entrySet().iterator().next();
        assertThat(pair1.getValue().id).isEqualTo(pair1.getKey());
        Map.Entry<MarketId, Market> pair2 = marketMap.entrySet().iterator().next();
        assertThat(pair2.getValue().id).isEqualTo(pair2.getKey());
    }
}
