package com.melotic.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class DealOrder
{
    public DealOrderId id;

    public DealState state;

    public DealVerb verb;

    public double price;

    public double total;

    public double amount;

    @JsonProperty("market_id")
    public MarketId marketId;

    @JsonProperty("deal_amount")
    public double dealAmount;

    @JsonProperty("deal_price")
    public double dealPrice;

    @JsonProperty("dealt_at")
    public Date dealtAt;
}
