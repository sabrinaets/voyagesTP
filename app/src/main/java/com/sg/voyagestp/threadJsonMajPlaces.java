package com.sg.voyagestp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class threadJsonMajPlaces extends Thread {
    private static final String TAG = "MajPlacesThread";
    final String URL_POINT_ENTREE = "http://10.0.2.2:3000/voyages";//"http://192.168.0.101:3000";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private int idVoyage;
    private String dateTrip; // Format "yyyy-MM-dd"
    private int nbPlacesReservees;
    private Context contexte;

    public threadJsonMajPlaces(Context contexte, int idVoyage, String dateTrip, int nbPlacesReservees) {
        this.contexte = contexte;
        this.idVoyage = idVoyage;
        this.dateTrip = dateTrip;
        this.nbPlacesReservees = nbPlacesReservees;
    }

    @Override
    public void run() {
        OkHttpClient client = new OkHttpClient();

        try {
            // 1. GET le voyage
            Request getRequest = new Request.Builder().url(URL_POINT_ENTREE + "/" + idVoyage).build();
            Response getResponse = client.newCall(getRequest).execute();
            String voyageJson = getResponse.body().string();
            Log.i("Le Voyage", voyageJson );

            JSONObject voyageObject = new JSONObject(voyageJson);
            JSONArray trips = voyageObject.getJSONArray("trips");

            for (int i = 0; i < trips.length(); i++) {
                JSONObject trip = trips.getJSONObject(i);
                Log.i("Comparaison Dates", "trip.getDate = " + trip.getString("date") + " vs " + dateTrip);
                if (trip.getString("date").equals(dateTrip)) {
                    int currentPlaces = trip.getInt("nb_places_disponibles");
                    trip.put("nb_places_disponibles", currentPlaces - nbPlacesReservees);
                    trips.put(i, trip);
                    break;
                }
            }
            Log.i("Le Voyage modifié", voyageObject.toString());
            // 2. PUT le voyage modifié
            RequestBody body = RequestBody.create(voyageObject.toString(), JSON);
            Request putRequest = new Request.Builder()
                    .url(URL_POINT_ENTREE +"/"+ idVoyage)
                    .put(body)
                    .build();

            Response putResponse = client.newCall(putRequest).execute();

            if (putResponse.code() == 200) {
                Log.i("threadJSON", "Voyage mis à jour avec succès !" );
            } else {
                Log.e("threadJSON", "Erreur PUT: " + putResponse.code());
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("threadJSON", "Erreur lors de la mise à jour du JSON : " + e.getMessage());
        }
    }
}
