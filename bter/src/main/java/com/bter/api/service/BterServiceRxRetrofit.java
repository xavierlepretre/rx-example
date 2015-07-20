package com.bter.api.service;

import com.bter.api.dto.TradingPairId;
import java.util.List;
import retrofit.http.GET;
import rx.Observable;

public interface BterServiceRxRetrofit
{
    @GET("/pairs") Observable<List<TradingPairId>> getPairs();
}
