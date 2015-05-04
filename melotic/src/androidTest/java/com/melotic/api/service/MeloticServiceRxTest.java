package com.melotic.api.service;

import android.test.AndroidTestCase;
import com.melotic.api.dto.MarketDealOrderList;
import com.melotic.api.dto.MarketId;
import com.melotic.api.dto.MarketMap;
import com.melotic.api.dto.PriceTicker;
import java.util.Iterator;

import static org.fest.assertions.api.Assertions.assertThat;

public class MeloticServiceRxTest extends AndroidTestCase
{
    MeloticServiceRx meloticServiceRx;

    @Override protected void setUp() throws Exception
    {
        super.setUp();
        meloticServiceRx = new MeloticServiceRx();
    }

    public void testServiceCreated()
    {
        assertThat(meloticServiceRx).isNotNull();
    }

    public void testCanFetchMarkets()
    {
        Iterator<MarketMap> marketMapIterator = meloticServiceRx.getMarkets().toBlocking().getIterator();

        assertThat(marketMapIterator.hasNext()).isTrue();
        MarketMap marketMap = marketMapIterator.next();
        assertThat(marketMap).isNotNull();
        assertThat(marketMap.size()).isGreaterThan(5);
        assertThat(marketMap).containsKey(new MarketId("bc-btc"));
        assertThat(marketMap.get(new MarketId("bc-btc")).title).isEqualTo("BC/BTC");

        assertThat(marketMapIterator.hasNext()).isFalse();
    }

    public void testCanFetchMarketPrice()
    {
        Iterator<PriceTicker> tickerIterator = meloticServiceRx.getMarketPrice(new MarketId("bc-btc")).toBlocking().getIterator();

        assertThat(tickerIterator.hasNext()).isTrue();
        PriceTicker ticker = tickerIterator.next();
        assertThat(ticker.highestBid).isGreaterThan(0);

        assertThat(tickerIterator.hasNext()).isFalse();
    }

    public void testCanFetchMarketDealOrderList()
    {
        Iterator<MarketDealOrderList> deaOrderIterator = meloticServiceRx.getDealOrders(new MarketId("bc-btc"), null, 3, null).toBlocking().getIterator();

        assertThat(deaOrderIterator.hasNext()).isTrue();
        MarketDealOrderList list = deaOrderIterator.next();
        assertThat(list.dealOrders.size()).isLessThanOrEqualTo(3);
        assertThat(list.marketId).isEqualTo(new MarketId("bc-btc"));
    }
}
