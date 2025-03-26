package com.sg.voyagestp;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class threadJSON extends Thread{

    private static final String TAG = "messagesThread";
    final String URL_POINT_ENTREE = "http://10.0.2.2:3000";
    private String email;
    private String password;

    public threadJSON(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request requete = new Request.Builder().url(URL_POINT_ENTREE+"/clients").build();


        Response response =null;
        try {
            response = client.newCall(requete).execute();
            ResponseBody responseBody = response.body();
            String jsonData = responseBody.string();
            Log.i(TAG,"Clients: "+jsonData);

            ObjectMapper mapper = new ObjectMapper();
            Client[] clients = mapper.readValue(jsonData,Client[].class);
            boolean trouve =false;
            for (Client c :clients){
                if (c.getEmail().equals(this.email) && c.getPassword().equals(this.password)){
                    trouve=true;
                    break;
                }
            }

            if (trouve){
                //traitement pour aller a la page d'accueil
            }
            else{
                //message utilisateur introuvable
            }
        }
        catch(Exception e){
           Log.d(TAG,e.getMessage());
            throw new RuntimeException();
        }

    }
}
