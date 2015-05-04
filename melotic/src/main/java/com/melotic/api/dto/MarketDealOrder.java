package com.melotic.api.dto;

public class MarketDealOrder
{
    public final MarketId marketId;
    public final DealOrder dealOrder;

    public MarketDealOrder(MarketId marketId, DealOrder dealOrder)
    {
        this.marketId = marketId;
        this.dealOrder = dealOrder;
    }
}
