package com.example.xavier.rxexample;

import com.melotic.api.dto.MarketDealOrderList;
import com.melotic.api.dto.MarketId;
import com.melotic.api.dto.OrderBy;
import java.util.Collection;
import rx.Observable;

public class MeloticMarketListRxOkActivity extends MeloticMarketListRxActivity
{
    @Override protected Observable<MarketDealOrderList> fetchDeals(
            Collection<MarketId> marketIds,
            OrderBy orderBy,
            Integer count,
            Integer startAt)
    {
        return meloticServiceRx.getManyDealOrders(marketIds, orderBy, count, startAt);
    }
}
