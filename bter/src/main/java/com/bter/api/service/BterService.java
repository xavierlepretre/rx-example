package com.bter.api.service;

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

}
