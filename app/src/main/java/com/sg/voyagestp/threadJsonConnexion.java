package com.sg.voyagestp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.voyagestp.modeles.Client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class threadJsonConnexion extends Thread {

    private static final String TAG = "connexionThread";
    final String URL_POINT_ENTREE = "http://10.0.2.2:3000";//"http://192.168.0.101:3000";
    private String email;
    private String password;

    Context contexte;

    public threadJsonConnexion(Context contexte, String email, String password) {
        this.email = email;
        this.password = password;
        this.contexte = contexte;

    }

    @Override
    public void run() {

        String utilisateurTrouve = verifierUtilisateur(this.email,this.password);
        if (this.contexte instanceof Activity){
            ((Activity) this.contexte).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (utilisateurTrouve != null){
                        Intent intent = new Intent(contexte, AccueilActivity.class);
                        intent.putExtra("idUtilisateur", utilisateurTrouve);
                        contexte.startActivity(intent);
                    }
                    else{
                        Toast.makeText(contexte, "Utilisateur introuvable", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public String verifierUtilisateur(String email, String mdp) {
        OkHttpClient client = new OkHttpClient();
        Request requete = new Request.Builder().url(URL_POINT_ENTREE + "/clients").build();


        Response response = null;
        try {
            response = client.newCall(requete).execute();
            ResponseBody responseBody = response.body();
            String jsonData = responseBody.string();
            Log.i(TAG, "Clients: " + jsonData);

            ObjectMapper mapper = new ObjectMapper();
            Client[] clients = mapper.readValue(jsonData, Client[].class);
            boolean trouve = false;
            for (Client c : clients) {
                if (c.getEmail().equals(email) && c.getPassword().equals(mdp)) {
                    return c.getId();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new RuntimeException();

        }
        return null;
    }
}