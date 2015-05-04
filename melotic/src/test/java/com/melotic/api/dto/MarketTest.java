package com.melotic.api.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.fest.assertions.data.Offset;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class MarketTest
{
    private ObjectMapper mapper;

    @Before public void setUp()
    {
        this.mapper = new ObjectMapper();
    }

    @Test public void testFieldsCorrect() throws IOException
    {
        Market market = mapper.readValue(getClass().getResourceAsStream("market_1.json"), Market.class);

        assertThat(market.id).isNull();
        assertThat(market.title).isEqualTo("BC/BTC");
        assertThat(market.highestBid).isEqualTo(0.3d, Offset.offset(0.00001d));
        assertThat(market.highestDealPrice).isEqualTo(0.2d, Offset.offset(0.00001d));
        assertThat(market.latestPrice).isEqualTo(0.1d, Offset.offset(0.00001d));
        assertThat(market.lowestAsk).isEqualTo(0.1d, Offset.offset(0.00001d));
        assertThat(market.lowestDealPrice).isEqualTo(0.2d, Offset.offset(0.00001d));
        assertThat(market.volume).isEqualTo(123d, Offset.offset(0.00001d));
        assertThat(market.priceChange).isEqualTo(0.00527815d, Offset.offset(0.00001d));
    }
}
