package com.bter.api.service;

import com.bter.api.dto.TradingPairId;
import java.util.List;
import retrofit.Callback;
import retrofit.http.GET;

public interface BterServiceRetrofit
{
    @GET("/pairs")
    void getPairs(Callback<List<TradingPairId>> callback);
}
