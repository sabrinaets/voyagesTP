package com.sg.voyagestp;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sg.voyagestp.modeles.Trip;
import com.sg.voyagestp.modeles.Voyage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class voyageViewModel extends ViewModel {

    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<List<Voyage>> voyages = new MutableLiveData<>();
    private List<Voyage> allVoyages = new ArrayList<>();

    public void init(Context contexte){
        if (!allVoyages.isEmpty()){return;}
        else{
            threadJsonVoyages thread = new threadJsonVoyages(contexte,this);
            thread.start();
        }
    }
    public void setVoyages(List<Voyage> list) {
        this.allVoyages = list;
        voyages.postValue(list);
    }

    public List<Voyage> getAllVoyages() {
        return allVoyages;
    }

    public MutableLiveData<List<Voyage>> getVoyages() {
        return voyages;
    }

    public void filtrer(String dest, String type, String date, String budget) {
        List<Voyage> resultats = new ArrayList<>();

        double budgetMax = Double.MAX_VALUE;
        if (!budget.equals("Aucun budget") && !budget.isEmpty()) {
            try {
                budgetMax = Double.parseDouble(budget);

            } catch (NumberFormatException e) {
                budgetMax = Double.MAX_VALUE; // en cas d'erreur on ignore le budget
            }
        }
        Log.i("filtrage","budget:"+budgetMax);
        for (Voyage v : allVoyages) {
            boolean correspond = true;

            // Vérifier destination
            if (!dest.equals("Tous") &&!dest.isEmpty() && !v.getDestination().equalsIgnoreCase(dest)) {
                correspond = false;
            }

            // Vérifier type
            if (!type.equals("Tous")&&!type.isEmpty() && !v.getType_de_voyage().equalsIgnoreCase(type)) {
                correspond = false;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());  // Format de la date : "2025-04-10"
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            // Vérifier la date dans les trips

            if (!date.isEmpty()) {
                Log.d("dates", "Date entrée par l'utilisateur : " + date);
                boolean dateMatch = false;
                for (Trip trip : v.getTrips()) {
                    try {
                        // Convertir la date du trip et la date de l'utilisateur en objets Date

                        Date userDate = sdf.parse(date);  // Date entrée par l'utilisateur
                        Date tripDate = trip.getDate();


                        Log.d("dates", "Avant reset - userDate: " + userDate + " tripDate: " + tripDate);
                        userDate = resetTime(userDate);
                        tripDate = resetTime(tripDate);
                        Log.d("dates", "Après reset - userDate: " + userDate + " tripDate: " + tripDate);


                        // Comparer les deux dates
                        if (tripDate.equals(userDate)) {
                            dateMatch = true;
                            break;
                        }
                    } catch (Exception e) {
                        // En cas d'erreur de format de date, ignorer cette comparaison
                        e.printStackTrace();
                        Log.e("dates", "Erreur lors du parsing de la date : " + e.getMessage());
                    }
                }
                if (!dateMatch) {
                    correspond = false;
                }
            }

            // Vérifier le budget
            if (v.getPrix() > budgetMax) {
                correspond = false;
            }

            if (correspond) {
                resultats.add(v);
            }
        }
        Log.d("Filtrage", "Voyages filtrés : " + resultats.size());
        voyages.setValue(resultats);
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public void setError(MutableLiveData<String> error) {
        this.error = error;
    }
    private Date resetTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
