package com.sg.voyagestp.modeles;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sg.voyagestp.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class reservationAdapter extends ArrayAdapter<Trip> {
    private Context contexte;
    private int viewResourceId;
    private Resources ressources;
    private final List<Trip> trips;
    TextView tvDate;
    TextView tvPlaces;

    public reservationAdapter(@NonNull Context context, int resource, List<Trip>liste) {
        super(context, resource,liste);
        this.contexte = context;
        this.viewResourceId = resource;
        this.ressources = contexte.getResources();
        this.trips = liste;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) contexte.getSystemService(Context.
                    LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(this.viewResourceId, parent, false);
        }

        Trip trip = trips.get(position);
        if (trip!=null){
            tvDate = view.findViewById(R.id.tvDate);
            tvPlaces=view.findViewById(R.id.tvNbPlaces);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(trip.getDate());  // Format de la date

            tvDate.setText("Date: "+formattedDate);
            tvPlaces.setText("Nombre de places: "+trip.getNb_places_disponibles());
        }
        return view;

    }
}
