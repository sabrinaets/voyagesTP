package com.sg.voyagestp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.voyagestp.modeles.Client;
import com.sg.voyagestp.modeles.Voyage;

import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class threadJsonVoyages extends Thread{
    private static final String TAG = "voyagesThread";
    final String URL_POINT_ENTREE = "http://192.168.0.101:3000";
    Context contexte;
    voyageViewModel viewModel;

    public threadJsonVoyages(Context contexte, voyageViewModel viewModel){
        this.contexte = contexte;
        this.viewModel = viewModel;
    }

    @Override
    public void run(){
        List<Voyage> voyages = allVoyages(); // JSON
        viewModel.setVoyages(voyages);

    }
    public List<Voyage> allVoyages(){
        OkHttpClient client = new OkHttpClient();
        Request requete = new Request.Builder().url(URL_POINT_ENTREE + "/voyages").build();

        Response response = null;
        try{
            response = client.newCall(requete).execute();
            ResponseBody responseBody = response.body();
            String jsonData = responseBody.string();
            Log.i(TAG, "Voyages: " + jsonData);

            ObjectMapper mapper = new ObjectMapper();
            Voyage[] voyages = mapper.readValue(jsonData, Voyage[].class);
            return Arrays.asList(voyages);
        }
        catch(Exception e){
            Log.e(TAG, e.getMessage());
            throw new RuntimeException();
        }
    }

}
