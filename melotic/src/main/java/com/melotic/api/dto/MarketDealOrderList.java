package com.melotic.api.dto;

import java.util.Collections;
import java.util.List;

public class MarketDealOrderList
{
    public final MarketId marketId;
    public final List<DealOrder> dealOrders;

    public MarketDealOrderList(MarketId marketId, List<DealOrder> dealOrders)
    {
        this.marketId = marketId;
        this.dealOrders = Collections.unmodifiableList(dealOrders);
    }
}
