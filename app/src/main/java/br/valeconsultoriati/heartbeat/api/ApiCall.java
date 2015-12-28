package br.valeconsultoriati.heartbeat.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import br.valeconsultoriati.heartbeat.configurations.ToolsToServices;

public class ApiCall
{

    public static JSONObject doCall(URL url, String params) throws Exception {
        String ret = openHttpConnection(url);

        if(ret.length() <= 2){
            ToolsToServices.setAction(false);
            return new JSONObject("{\"re\" : false}");
        }else {
            JSONArray array = new JSONArray(ret.toString());
            return array.getJSONObject(0);
        }
    }

    public static JSONObject getApplicationErrorReturn() throws JSONException
    {
        return new JSONObject("{\"re\": \"false\", \"application_message\": \"{{translate.message.application.application_error}}\"}");
    }

    private static String openHttpConnection(URL urlStr) {
        InputStream in = null;
        int resCode = -1;

        try {
            URLConnection urlConn = urlStr.openConnection();
            urlConn.setConnectTimeout(3000);

            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }else{
                //TODO -- Fazer notificacao qdo nao acessar link
            }
        }

        catch (MalformedURLException e) {
            e.printStackTrace();
        }catch(SocketTimeoutException s){
            return ToolsToServices.buildReturn("Time out, internet funcionando?");
        }catch(UnknownHostException uh){
            return ToolsToServices.buildReturn("Host desconhecido, internet funcionando?");
        } catch(ConnectException c){
            return ToolsToServices.buildReturn("Algo estranho na URL, internet funcionando?");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return convertInputStream(in);
    }



    private static String convertInputStream(InputStream in) {
        StringBuilder ret = new StringBuilder();
        String data;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            while ((data = reader.readLine()) != null){
                ret.append(data);
            }
            in.close();
        }

        catch (IOException e1) {
            e1.printStackTrace();
        }
        return ret.toString();
    }

}
