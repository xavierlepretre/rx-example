package com.bter.api.service;

import com.bter.api.dto.MarketInfo;
import com.bter.api.dto.MarketList;
import com.bter.api.dto.TradingPairId;
import java.util.List;
import rx.Observable;

public class BterServiceRx
{
    final BterServiceRxRetrofit service;

    public BterServiceRx()
    {
        this(BterRestAdapter.builder()
                .build()
                .create(BterServiceRxRetrofit.class));
    }

    public BterServiceRx(BterServiceRxRetrofit service)
    {
        this.service = service;
    }

    public Observable<List<TradingPairId>> getPairs()
    {
        return service.getPairs();
    }

    public Observable<MarketInfo> getMarketInfo()
    {
        return service.getMarketInfo();
    }

    public Observable<MarketList> getMarketList()
    {
        return service.getMarketList();
    }
}
