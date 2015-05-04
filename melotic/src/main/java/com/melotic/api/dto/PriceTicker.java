package com.melotic.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceTicker
{
    @JsonProperty("highest_bid")
    public double highestBid;

    @JsonProperty("highest_deal_price")
    public double highestDealPrice;

    @JsonProperty("latest_price")
    public double latestPrice;

    @JsonProperty("lowest_ask")
    public double lowestAsk;

    @JsonProperty("lowest_deal_price")
    public double lowestDealPrice;

    public double volume;

    @JsonProperty("price_change")
    public double priceChange;
}
