package com.example.srava.iutinder;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.NetworkOnMainThreadException;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lagonl on 07/03/2016.
 */
class HttpRequestTaskManager extends AsyncTask<Credential, String, JSONObject> {

    private static final String FLAG_SUCCESS = "success";
    private static final String FLAG_MESSAGE = "message";
    private static final String LOGIN_URL = "http://iutinder.16mb.com/";

    TextView connectionStatus;

    public void setConnectionStatus(TextView connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    @Override
    protected JSONObject doInBackground(Credential... params) {
        JSONObject jsonResponse = new JSONObject();
        try {
            //Recupere l'adresse url du site
            URL url = new URL(LOGIN_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            //Recupere le credential passe en parametre
            Credential credential = params[0];
            String urlParameters = "username=" + credential.username + "&password=" + credential.password;
            //Recupere les donnees en paraletre
            byte[] postData = urlParameters.getBytes();
            connection.setRequestProperty("Content-Length", "" + postData.length);
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.write(postData);
            }
            // envoie des donnees
            Log.d("HttpRequestTaskBackgr", "ready to send request.");
            connection.connect();
            // decode response
            InputStream in = new BufferedInputStream(connection.getInputStream());
            jsonResponse = new JSONObject(convertStreamToString(in));

        } catch (IOException e) {
            Log.e("IOException", "Error");
        } catch (JSONException e) {
            Log.e("JSONException", "Error");
        } catch (NetworkOnMainThreadException e) {
            connectionStatus.setText("Marche pas si android > 3.0!!");
        }
        return jsonResponse;
    }


    @Override
    protected void onPostExecute( JSONObject result){

        try{
            Log.d("result",result.getString(FLAG_SUCCESS));
            int loginOK = result.getInt(FLAG_SUCCESS);
            connectionStatus.setText(result.getString(FLAG_MESSAGE));

            // check if connection status is OK
            if(loginOK!=0)
            {
                connectionStatus.setText("Connecté");
            }
            else
            {
                connectionStatus.setText("Erreur de connexion");
            }

        }  catch(JSONException e){
            Log.e("JSONException", "Error");
        }  catch (NetworkOnMainThreadException e){
            Log.e("ThreadException", "android > 3.0!!");
        }
    }

    private static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


}
