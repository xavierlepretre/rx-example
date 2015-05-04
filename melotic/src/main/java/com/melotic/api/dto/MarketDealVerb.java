package com.melotic.api.dto;

public class MarketDealVerb
{
    public final MarketId marketId;
    public final DealVerb dealVerb;

    public MarketDealVerb(MarketId marketId, DealVerb dealVerb)
    {
        this.marketId = marketId;
        this.dealVerb = dealVerb;
    }
}
