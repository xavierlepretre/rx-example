package com.bter.api.service;

import com.bter.api.dto.MarketInfo;
import com.bter.api.dto.MarketList;
import com.bter.api.dto.TradingPairId;
import java.util.List;
import retrofit.Callback;
import retrofit.http.GET;

public interface BterServiceRetrofit
{
    @GET("/pairs")
    void getPairs(Callback<List<TradingPairId>> callback);

    @GET("/marketinfo")
    void getMarketInfo(Callback<MarketInfo> callback);

    @GET("/marketlist")
    void getMarketList(Callback<MarketList> callback);
}
