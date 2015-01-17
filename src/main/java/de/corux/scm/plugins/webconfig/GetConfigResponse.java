package de.corux.scm.plugins.webconfig;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Response object of the GET call.
 */
public class GetConfigResponse
{
    private boolean success;
    private Dictionary<String, String> data;

    public GetConfigResponse()
    {
        data = new Hashtable<String, String>();
    }

    public boolean getSuccess()
    {
        return success;
    }

    public void setSuccess(final boolean success)
    {
        this.success = success;
    }

    public Dictionary<String, String> getData()
    {
        return data;
    }

    public void setData(final Dictionary<String, String> data)
    {
        this.data = data;
    }

}
