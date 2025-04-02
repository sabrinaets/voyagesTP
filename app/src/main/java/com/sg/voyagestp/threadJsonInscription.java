package com.sg.voyagestp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.voyagestp.modeles.Client;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class threadJsonInscription extends Thread {
    private static final String TAG = "inscriptionThread";
    final String URL_POINT_ENTREE = "http://192.168.0.101:3000";
    private Client client;

    Context contexte;

    public threadJsonInscription(Context contexte, Client nouvClient) {
        this.client = nouvClient;
        this.contexte = contexte;
    }

    @Override
    public void run() {
        boolean userAdded=false;
        boolean utilisateurExiste = verifierSiExiste(this.client); // verifier si l'utilisateur existe deja
        if (!utilisateurExiste){
            userAdded = ajouterUtilisateur(client);
        }
        boolean finalUserAdded = userAdded;
        if (this.contexte instanceof Activity){
            ((Activity) this.contexte).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (utilisateurExiste){
                        Toast.makeText(contexte,"Un utilisateur avec ce email existe deja!",Toast.LENGTH_SHORT).show();
                    }
                    else{

                        if (finalUserAdded){
                            Toast.makeText(contexte, "Compte cree avec succes!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(contexte, MainActivity.class);
                            contexte.startActivity(intent);
                        }
                        else{
                            Toast.makeText(contexte, "Erreur lors de la creation du compte", Toast.LENGTH_SHORT).show();
                        }


                    }
            }

        });
    }
    }

    public boolean verifierSiExiste(Client nouvClient){
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

            for (Client c : clients) {
                if (c.getEmail().equals(nouvClient.getEmail())) {
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new RuntimeException();

        }
        return false;
    }

    public boolean ajouterUtilisateur(Client user){
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; chartset=utf-8");

        try{
            JSONObject obj = new JSONObject();
            obj.put("nom",user.getNom());
            obj.put("prenom",user.getPrenom());
            obj.put("email",user.getEmail());
            obj.put("mdp",user.getPassword());
            obj.put("age",user.getAge());
            obj.put("telephone",user.getTelephone());
            obj.put("adresse",user.getAdresse());

            RequestBody corpsRequete = RequestBody.create(String.valueOf(obj), JSON);
            Request request = new Request.Builder().url(URL_POINT_ENTREE + "/clients")
                    .post(corpsRequete)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 201){
                return true;
            }
            return false;
        }
        catch(Exception e){
            Log.e(TAG, "Erreur lors de l'ajout d'un utilisateur",e);
            throw new RuntimeException();
        }
    }
}
