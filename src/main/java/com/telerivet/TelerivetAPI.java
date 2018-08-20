/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telerivet;

import com.telerivet.exception.TelerivetAPIException;
import com.telerivet.exception.TelerivetInvalidParameterException;
import com.telerivet.exception.TelerivetNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipCompressingEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.ContentEncodingHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author youngj
 */
public class TelerivetAPI {
    public static String CLIENT_VERSION = "1.4.2";

    public static final int HTTP_CONNECTION_TIMEOUT = 10000; // ms
    public static final int HTTP_SOCKET_TIMEOUT = 10000; // ms

    protected String apiKey;
    protected String apiUrl;
    protected int numRequests = 0;

    private HttpClient httpClient;

    /**
        <p>Initializes a client handle to the Telerivet REST API.</p>
        
        <p>Each API key is associated with a Telerivet user account, and all
        API actions are performed with that user's permissions. If you want to restrict the
        permissions of an API client, simply add another user account at
        <a href="https://telerivet.com/dashboard/users">https://telerivet.com/dashboard/users</a> with the desired permissions.</p>
    */
    public TelerivetAPI(String apiKey)
    {
        this(apiKey, "https://api.telerivet.com/v1");
    }

    public TelerivetAPI(String apiKey, String apiUrl)
    {
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
    }

    public int getNumRequests()
    {
        return this.numRequests;
    }

    /**
        <p>Retrieves the Telerivet project with the given ID.</p>
    */
    public Project getProjectById(String id) throws IOException
    {
        return new Project(this, (JSONObject) this.doRequest("GET", getBaseApiPath() + "/projects/" + id));
    }

    /**
        <p>Initializes the Telerivet project with the given ID without making an API request.</p>
    */
    public Project initProjectById(String id)
    {
        return new Project(this, Util.options("id", id), false);
    }

    /**
        <p>Queries projects accessible to the current user account.</p>
    */
    public APICursor<Project> queryProjects(JSONObject options)
    {
        return this.newCursor(Project.class, getBaseApiPath() + "/projects", options);
    }

    public APICursor<Project> queryProjects()
    {
        return queryProjects(null);
    }

    /**
        <p>Retrieves the Telerivet organization with the given ID.</p>
    */
    public Organization getOrganizationById(String id) throws IOException
    {
        return new Organization(this, (JSONObject) this.doRequest("GET", getBaseApiPath() + "/organizations/" + id));
    }

    /**
        <p>Initializes the Telerivet organization with the given ID without making an API request.</p>
    */
    public Organization initOrganizationById(String id)
    {
        return new Organization(this, Util.options("id", id), false);
    }

    /**
        <p>Queries organizations accessible to the current user account.</p>
    */
    public APICursor<Organization> queryOrganizations(JSONObject options)
    {
        return this.newCursor(Organization.class, getBaseApiPath() + "/organizations", options);
    }

    public APICursor<Organization> queryOrganizations()
    {
        return queryOrganizations(null);
    }

    public String getBaseApiPath()
    {
        return "";
    }
    protected <T extends Entity> APICursor<T> newCursor(Class<T> itemClass, String path, JSONObject options)
    {
        return new APICursor<T>(this, itemClass, path, options);
    }

    private void encodeParamsRec(String paramName, Object value, List<BasicNameValuePair> paramArr) throws JSONException
    {
        if (value == null || value.equals(JSONObject.NULL))
        {
            return;
        }
        if (value instanceof JSONArray)
        {
            JSONArray arr = (JSONArray)value;
            int len = arr.length();
            for (int i = 0; i < len; i++)
            {
                encodeParamsRec(paramName + "[" +i + "]", arr.get(i), paramArr);
            }
        }
        else if (value instanceof JSONObject)
        {
            JSONObject obj = (JSONObject)value;
            Iterator keysIter = obj.keys();
            while(keysIter.hasNext())
            {
                String key = (String)keysIter.next();
                encodeParamsRec(paramName + "[" + key + "]", obj.get(key), paramArr);
            }
        }
        else if (value instanceof Number)
        {
            Number num = (Number)value;
            int intValue = num.intValue();
            if ((double)intValue == num.doubleValue())
            {
                paramArr.add(new BasicNameValuePair(paramName, "" + intValue));
            }
            else
            {
                paramArr.add(new BasicNameValuePair(paramName, value.toString()));
            }
        }
        else
        {
            paramArr.add(new BasicNameValuePair(paramName, value.toString()));
        }
    }

    private List<BasicNameValuePair> encodeParams(JSONObject jsonOptions) throws JSONException
    {
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

        Iterator keysIter = jsonOptions.keys();
        while(keysIter.hasNext())
        {
            String key = (String)keysIter.next();
            encodeParamsRec(key, jsonOptions.get(key), params);
        }
        return params;
    }

    public Object doRequest(String method, String path) throws IOException
    {
        return doRequest(method, path, null);
    }

    public Object doRequest(String method, String path, JSONObject params) throws IOException
    {
        HttpUriRequest request;
        String url = apiUrl + path;

        if ("POST".equals(method) || "PUT".equals(method))
        {
            HttpEntityEnclosingRequestBase entityRequest;
            if ("PUT".equals(method))
            {
                entityRequest = new HttpPut(url);
            }
            else
            {
                entityRequest = new HttpPost(url);
            }

            entityRequest.setHeader("Content-Type", "application/json");
            if (params != null)
            {
                String paramsJson = params.toString();
                HttpEntity entity = new StringEntity(paramsJson, "UTF-8");
                if (paramsJson.length() >= 400)
                {
                    entity = new GzipCompressingEntity(entity);
                }
                entityRequest.setEntity(entity);
            }
            request = entityRequest;
        }
        else
        {
            if (params != null && params.length() > 0)
            {
                url = url + "?" + URLEncodedUtils.format(encodeParams(params), "UTF-8");
            }

            if ("GET".equals(method))
            {
                request = new HttpGet(url);
            }
            else if ("DELETE".equals(method))
            {
                request = new HttpDelete(url);
            }
            else
            {
                throw new InvalidParameterException("Invalid HTTP method");
            }
        }

        return doRequest(request);
    }

    private Object doRequest(HttpUriRequest request) throws UnsupportedEncodingException, JSONException, IOException
    {
        String authParams = apiKey + ":";

        Base64 base64 = new Base64();
        String authString = base64.encodeToString(authParams.getBytes("UTF-8"));

        request.addHeader("Authorization", "Basic " + authString);
        request.setHeader("User-Agent", "Telerivet Java Client/" + CLIENT_VERSION + " Java/" + System.getProperty("java.version"));

        HttpClient client = getHttpClient();

        HttpResponse response;

        this.numRequests++;

        try
        {
            response = client.execute(request);
        }
        catch (UnknownHostException ex)
        {
            throw new IOException("Could not connect to Telerivet API: " + ex.getMessage());
        }
        catch (IOException ex)
        {
            throw new IOException("Could not connect to Telerivet API: " + ex.getMessage());
        }

        int statusCode = response.getStatusLine().getStatusCode();

        String responseStr = EntityUtils.toString(response.getEntity());

        Object responseData = new JSONTokener(responseStr).nextValue();

        if (statusCode == 200)
        {
            return responseData;
        }
        else
        {
            JSONObject responseObj = (JSONObject)responseData;

            JSONObject error = responseObj.optJSONObject("error");
            if (error != null)
            {
                String code = error.optString("code");
                String message = error.optString("message");
                if ("invalid_param".equals(code))
                {
                    throw new TelerivetInvalidParameterException(message, code);
                }
                else if ("not_found".equals(code))
                {
                    throw new TelerivetNotFoundException(message, code);
                }
                else
                {
                    throw new TelerivetAPIException(message, code);
                }
            }
            else
            {
                throw new TelerivetAPIException("Telerivet API error (HTTP " + statusCode + ")", null);
            }
        }
    }

    private HttpClient getHttpClient()
    {
        if (httpClient == null)
        {
            // via http://thinkandroid.wordpress.com/2009/12/31/creating-an-http-client-example/
            // also http://hc.apache.org/httpclient-3.x/threading.html

            HttpParams httpParams = getDefaultHttpParams();

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

            final SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
            sslSocketFactory.setHostnameVerifier(SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

            registry.register(new Scheme("https", sslSocketFactory, 443));

            ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(httpParams, registry);

            httpClient = new ContentEncodingHttpClient(manager, httpParams);
        }
        return httpClient;
    }

    private HttpParams getDefaultHttpParams()
    {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, HTTP_SOCKET_TIMEOUT);
        HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
        return httpParams;
    }
}