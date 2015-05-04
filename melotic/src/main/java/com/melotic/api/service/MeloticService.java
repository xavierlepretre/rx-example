package com.melotic.api.service;

import com.melotic.api.dto.DealOrder;
import com.melotic.api.dto.DealVerb;
import com.melotic.api.dto.MarketDealOrderList;
import com.melotic.api.dto.MarketDepth;
import com.melotic.api.dto.MarketId;
import com.melotic.api.dto.MarketMap;
import com.melotic.api.dto.OrderBy;
import com.melotic.api.dto.PriceTicker;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MeloticService
{
    private final MeloticServiceRetrofit service;

    public MeloticService()
    {
        this.service = MeloticRestAdapter.builder()
                .build()
                .create(MeloticServiceRetrofit.class);
    }

    public void getMarkets(final Callback<MarketMap> callback)
    {
        service.getMarkets(callback);
    }

    public void getMarketPrice(
            MarketId marketId,
            Callback<PriceTicker> callback)
    {
        service.getMarketPrice(marketId.key, callback);
    }

    public void getMarketDepth(
            MarketId marketId,
            DealVerb verb,
            Integer count,
            Callback<List<MarketDepth>> callback)
    {
        switch (verb)
        {
            case buy:
                getBuyDepth(marketId, count, callback);
                break;
            case sell:
                getSellDepth(marketId, count, callback);
                break;
        }
    }

    public void getBuyDepth(
            MarketId marketId,
            Integer count,
            Callback<List<MarketDepth>> callback)
    {
        service.getBuyDepth(marketId.key, count, callback);
    }

    public void getSellDepth(
            MarketId marketId,
            Integer count,
            Callback<List<MarketDepth>> callback)
    {
        service.getSellDepth(marketId.key, count, callback);
    }

    public void getDealOrders(
            final MarketId marketId,
            OrderBy orderBy,
            Integer count,
            Integer startAt,
            final Callback<MarketDealOrderList> callback)
    {
        service.getDealOrders(
                marketId.key,
                orderBy == null ? null : orderBy.apiCode,
                count,
                startAt,
                new Callback<List<DealOrder>>()
                {
                    @Override public void success(List<DealOrder> dealOrders, Response response)
                    {
                        callback.success(new MarketDealOrderList(marketId, dealOrders), response);
                    }

                    @Override public void failure(RetrofitError error)
                    {
                        callback.failure(error);
                    }
                });
    }
}
