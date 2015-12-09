package br.valeconsultoriati.heartbeat.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by vinicius on 29/03/15.
 */
public interface AsyncApiStatus {

    public boolean isRun();

    public JSONObject getResult();

    public URL getURL();

    public boolean isSuccess();

    public boolean finishSuccess();

    public JSONObject getApplicationErrorReturn() throws JSONException;

}
