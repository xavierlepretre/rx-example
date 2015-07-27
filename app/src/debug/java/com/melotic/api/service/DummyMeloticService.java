package com.melotic.api.service;

public class DummyMeloticService extends MeloticService
{
    public DummyMeloticService()
    {
        super(new DummyMeloticServiceRetrofit());
    }
}
