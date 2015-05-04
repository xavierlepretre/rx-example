package com.melotic.api.service;

class Counter
{
    private int c = 0;

    void plus()
    {
        c++;
    }

    void minus()
    {
        c--;
    }

    int getC()
    {
        return c;
    }
}
