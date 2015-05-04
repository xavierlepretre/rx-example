package com.melotic.api.dto;

import android.annotation.TargetApi;
import android.os.Build;
import java.io.IOException;
import org.apache.commons.io.IOUtils;

public class ResourceUtil
{
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getTextResource(String relPath)
    {
        String result = "";

        ClassLoader classLoader = ResourceUtil.class.getClassLoader();
        try
        {
            result = IOUtils.toString(classLoader.getResourceAsStream(relPath),"UTF-8");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return result;
    }
}
