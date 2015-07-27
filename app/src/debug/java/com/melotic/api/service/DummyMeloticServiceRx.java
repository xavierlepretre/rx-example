package com.melotic.api.service;

public class DummyMeloticServiceRx extends MeloticServiceRx
{
    public DummyMeloticServiceRx()
    {
        super(new DummyMeloticServiceRxRetrofit());
    }
}
