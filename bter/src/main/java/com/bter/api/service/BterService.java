package com.bter.api.service;

import com.bter.api.dto.MarketInfo;
import com.bter.api.dto.MarketList;
import com.bter.api.dto.TradingPairId;
import java.util.List;
import retrofit.Callback;

public class BterService
{
    private final BterServiceRetrofit service;

    public BterService()
    {
        this.service = BterRestAdapter.builder()
                .build()
                .create(BterServiceRetrofit.class);
    }

    public void getPairs(Callback<List<TradingPairId>> callback)
    {
        service.getPairs(callback);
    }

    public void getMarketInfo(Callback<MarketInfo> callback)
    {
        service.getMarketInfo(callback);
    }

    public void getMarketList(Callback<MarketList> callback)
    {
        service.getMarketList(callback);
    }
}
