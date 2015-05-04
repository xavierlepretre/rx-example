package com.melotic.api.service;

import com.example.xavier.melotic.BuildConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;

public class MeloticRestAdapter
{
    public static RestAdapter.Builder builder()
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return new RestAdapter.Builder()
                .setEndpoint(MeloticConstants.PATH_PREFIX)
                .setConverter(new JacksonConverter(mapper))
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.HEADERS);
    }
}
