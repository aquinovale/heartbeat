package br.valeconsultoriati.heartbeat.api;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import br.valeconsultoriati.heartbeat.configurations.HeartbeatModel;
import br.valeconsultoriati.heartbeat.configurations.Urls;

public class HeartbeatApi implements AsyncApiStatus
{

    private final String TAG = this.getClass().getSimpleName();

    private AsyncApiCall request;


    public HeartbeatApi(Context ctx) {
        try {
            this.request = new AsyncApiCall(ctx, new URL(Urls.getBaseUrl()), "");
        } catch (MalformedURLException e) {
            Log.e(TAG, "> url: " + Urls.getBaseUrl());
        }
        this.request.execute();
    }

    public HeartbeatApi(Context ctx, String data) {
        Log.v(TAG, "> "+data.toString());
        try {
            this.request = new AsyncApiCall(ctx, new URL(Urls.getBaseUrl()), data);
        } catch (MalformedURLException e) {
            Log.e(TAG, "> url: " + Urls.getBaseUrl());
        }
        this.request.execute();
    }

    public boolean isCritical(){
        JSONObject json = getResult();
        try {
            boolean critico = json.getBoolean(HeartbeatModel.CRITICO.toString());
            Log.v(TAG, "> isCritial(): " + critico);
            return critico;
        } catch (JSONException e) {
            Log.v(TAG, "> isCritial(): " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean finishSuccess() {
        return request.finishSuccess();
    }

    @Override
    public boolean isRun() {
        return request.isRun();
    }

    @Override
    public JSONObject getResult() {
        return request.getResult();
    }

    @Override
    public URL getURL() {
        return request.getURL();
    }

    @Override
    public boolean isSuccess() {
        return request.isSuccess();
    }

    @Override
    public JSONObject getApplicationErrorReturn() throws JSONException {
        return request.getApplicationErrorReturn();
    }
}
