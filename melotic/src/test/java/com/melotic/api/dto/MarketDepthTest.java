package com.melotic.api.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.fest.assertions.data.Offset;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class MarketDepthTest
{
    private ObjectMapper mapper;

    @Before public void setUp()
    {
        this.mapper = new ObjectMapper();
    }

    @Test public void testFieldsCorrect() throws IOException
    {
        List<MarketDepth> marketDepths = mapper.readValue(getClass().getResourceAsStream("market_depth_list_3.json"), MarketDepthList.class);

        assertThat(marketDepths.size()).isEqualTo(3);

        MarketDepth depth0 = marketDepths.get(0);
        assertThat(depth0.amount).isEqualTo(6.0d, Offset.offset(0.00001d));
        assertThat(depth0.price).isEqualTo(2.0d, Offset.offset(0.00001d));
        assertThat(depth0.total).isEqualTo(12.0d, Offset.offset(0.00001d));

        MarketDepth depth1 = marketDepths.get(1);
        assertThat(depth1.amount).isEqualTo(3.0d, Offset.offset(0.00001d));
        assertThat(depth1.price).isEqualTo(1.0d, Offset.offset(0.00001d));
        assertThat(depth1.total).isEqualTo(3.0d, Offset.offset(0.00001d));

        MarketDepth depth2 = marketDepths.get(2);
        assertThat(depth2.amount).isEqualTo(2.0d, Offset.offset(0.00001d));
        assertThat(depth2.price).isEqualTo(3.0d, Offset.offset(0.00001d));
        assertThat(depth2.total).isEqualTo(6.0d, Offset.offset(0.00001d));
    }

    private static class MarketDepthList extends ArrayList<MarketDepth>
    {
    }
}
