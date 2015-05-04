package com.melotic.api.dto;

public enum OrderBy
{
    DESC("desc"),
    ASC("asc"),
    ;

    public final String apiCode;

    private OrderBy(String apiCode)
    {
        this.apiCode = apiCode;
    }
}
