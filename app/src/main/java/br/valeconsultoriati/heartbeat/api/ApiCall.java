package br.valeconsultoriati.heartbeat.api;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ApiCall
{

    public static JSONObject doCall(URL url, String params) throws Exception {
        InputStream in = null;
        String data;
        StringBuilder ret = new StringBuilder();
        try {
            in = openHttpConnection(url);


            BufferedReader reader =new BufferedReader(new InputStreamReader(in, "UTF-8"));

            while ((data = reader.readLine()) != null){
                ret.append(data);
            }

            Log.d("ApiCall", "> " + ret);
            in.close();
        }

        catch (IOException e1) {
            e1.printStackTrace();
        }
        return new JSONObject(ret.toString());
    }

    public static JSONObject getApplicationErrorReturn() throws JSONException
    {
        return new JSONObject("{\"re\": \"false\", \"application_message\": \"{{translate.message.application.application_error}}\"}");
    }

    private static InputStream openHttpConnection(URL urlStr) {
        InputStream in = null;
        int resCode = -1;

        try {
            URLConnection urlConn = urlStr.openConnection();

            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        }

        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

}
