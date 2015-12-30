package br.valeconsultoriati.heartbeat.api;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import br.valeconsultoriati.heartbeat.configurations.ToolsToServices;

/**
 * Created by vinicius on 29/03/15.
 */
public class AsyncApiCall  extends AsyncTask<Void, Void, JSONObject> implements AsyncApiStatus{


    private final String TAG = this.getClass().getSimpleName();

    private boolean run = false;
    private boolean success = false;

    private URL url;
    private JSONObject result;
    private Context ctx;

    private String json;

    protected AsyncApiCall(Context ctx, URL url, String json) {
        this.ctx = ctx;
        this.url = url;
        this.json = json;
    }

    @Override
    public boolean finishSuccess(){
        return this.run ? false : this.success;
    }

    @Override
    public JSONObject getApplicationErrorReturn() throws JSONException{
        return this.getApplicationErrorReturn();
    }

    @Override
    public JSONObject getResult(){
        return this.result;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        Log.v(TAG, "Call API: " + url.toString());

        this.success = false;
        try {
            if(ToolsToServices.checkInternetConnection(ctx)){
                return ApiCall.doCall(this.url, this.json);
            }else{
                Log.w(TAG, "> : Sem acesso a internet");
                return new JSONObject(ToolsToServices.buildJSON("Internet não está funcionando"));
            }

        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "> : " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "> : " + e.getMessage());
        }
        return new JSONObject();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        setDefault();
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
        this.run = false;
        Log.v(TAG, "> " + result);
        try {
            this.success = result.getBoolean("re");
            receiveExtraCommands(result);
        } catch (Exception e) {
            Log.e(TAG, "> Exception: "+e.getMessage());
            this.success = false;
        }
    }

    private void receiveExtraCommands(JSONObject result) throws JSONException {
        //TODO - Receive result and execute webService to url that come.
        Log.d(TAG, "> " + result);
        this.result = result;

    }

    protected void setDefault() {
        this.run = true;
        this.success = false;
    }


    @Override
    public boolean isRun() {
        return this.run;
    }

    @Override
    public URL getURL() {
        return this.url;
    }

    @Override
    public boolean isSuccess() {
        return this.success;
    }

}
