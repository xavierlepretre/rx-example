package com.melotic.api.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.fest.assertions.data.Offset;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class DealOrderTest
{
    private ObjectMapper mapper;

    @Before public void setUp()
    {
        this.mapper = new ObjectMapper();
    }

    @Test public void testFieldsCorrect() throws IOException
    {
        List<DealOrder> DealOrders = mapper.readValue(getClass().getResourceAsStream("deal_order_list_1.json"), DealOrderList.class);

        assertThat(DealOrders.size()).isEqualTo(1);

        DealOrder dealOrder0 = DealOrders.get(0);
        assertThat(dealOrder0.id).isEqualTo(new DealOrderId(12091307));
        assertThat(dealOrder0.state).isEqualTo(DealState.dealt);
        assertThat(dealOrder0.price).isEqualTo(0.00853955d, Offset.offset(0.0000001d));
        assertThat(dealOrder0.total).isEqualTo(0.00888113d, Offset.offset(0.0000001d));
        assertThat(dealOrder0.amount).isEqualTo(1.04d, Offset.offset(0.0000001d));
        assertThat(dealOrder0.marketId).isEqualTo(new MarketId("ltc-btc"));
        assertThat(dealOrder0.dealAmount).isEqualTo(1.04d, Offset.offset(0.000001d));
        assertThat(dealOrder0.dealPrice).isEqualTo(0.00853955d, Offset.offset(0.000001d));
        assertThat(dealOrder0.dealtAt.getMinutes()).isEqualTo(17);
    }

    private static class DealOrderList extends ArrayList<DealOrder>
    {
    }
}
