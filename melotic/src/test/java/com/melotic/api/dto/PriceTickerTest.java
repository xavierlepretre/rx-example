package com.melotic.api.dto;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.fest.assertions.data.Offset;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class PriceTickerTest
{
    private ObjectMapper mapper;

    @Before public void setUp()
    {
        this.mapper = new ObjectMapper();
    }

    @Test public void testFieldsCorrect() throws IOException
    {
        PriceTicker ticker = mapper.readValue(getClass().getResourceAsStream("price_ticker1.json"), PriceTicker.class);

        assertThat(ticker.highestBid).isEqualTo(0.3d, Offset.offset(0.0001d));
        assertThat(ticker.highestDealPrice).isEqualTo(0.2d, Offset.offset(0.0001d));
        assertThat(ticker.latestPrice).isEqualTo(0.1d, Offset.offset(0.0001d));
        assertThat(ticker.lowestAsk).isEqualTo(0.1d, Offset.offset(0.0001d));
        assertThat(ticker.lowestDealPrice).isEqualTo(0.2d, Offset.offset(0.0001d));
        assertThat(ticker.volume).isEqualTo(123d, Offset.offset(0.0001d));
        assertThat(ticker.priceChange).isEqualTo(-0.02345992d, Offset.offset(0.0001d));
    }
}
