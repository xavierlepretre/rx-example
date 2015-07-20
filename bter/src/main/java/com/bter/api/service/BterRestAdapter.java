package com.bter.api.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xavierlepretre.bter.BuildConfig;
import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;

public class BterRestAdapter
{
    public static RestAdapter.Builder builder()
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return new RestAdapter.Builder()
                .setEndpoint(BterConstants.PATH_PREFIX)
                .setConverter(new JacksonConverter(mapper))
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.HEADERS);
    }
}
