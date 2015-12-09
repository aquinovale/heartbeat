package br.valeconsultoriati.heartbeat.api;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import br.valeconsultoriati.heartbeat.configurations.Urls;

public class HeartbeatApi implements AsyncApiStatus
{

    private final String TAG = this.getClass().getSimpleName();

    private AsyncApiCall request;


    public HeartbeatApi() {
        try {
            this.request = new AsyncApiCall(new URL(Urls.getEndingPoint(Urls.SERVICES)), "");
        } catch (MalformedURLException e) {
            Log.e(TAG, "> url: " + Urls.SERVICES.toString());
        }
        this.request.execute();
    }

    public HeartbeatApi(String data) {
        Log.v(TAG, "> "+data.toString());
        try {
            this.request = new AsyncApiCall(new URL(Urls.SERVICES.toString()), data);
        } catch (MalformedURLException e) {
            Log.e(TAG, "> url: " + Urls.SERVICES.toString());
        }
        this.request.execute();
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
