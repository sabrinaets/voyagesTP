package com.sg.voyagestp;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sg.voyagestp.modeles.Trip;
import com.sg.voyagestp.modeles.Voyage;

import java.util.ArrayList;
import java.util.List;

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
        if (!budget.isEmpty()) {
            try {
                budgetMax = Double.parseDouble(budget);
            } catch (NumberFormatException e) {
                budgetMax = Double.MAX_VALUE; // en cas d'erreur on ignore le budget
            }
        }

        for (Voyage v : allVoyages) {
            boolean correspond = true;

            // Vérifier destination
            if (!dest.isEmpty() && !v.getDestination().equalsIgnoreCase(dest)) {
                correspond = false;
            }

            // Vérifier type
            if (!type.isEmpty() && !v.getType_de_voyage().equalsIgnoreCase(type)) {
                correspond = false;
            }

            // Vérifier la date dans les trips
            if (!date.isEmpty()) {
                boolean dateMatch = false;
                for (Trip trip : v.getTrips()) {
                    if (trip.getDate().toString().equalsIgnoreCase(date)) {
                        dateMatch = true;
                        break;
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

        voyages.setValue(resultats);
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public void setError(MutableLiveData<String> error) {
        this.error = error;
    }
}
