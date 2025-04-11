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

public class reservationAdapter extends ArrayAdapter<Reservation> {
    private Context contexte;
    private int viewResourceId;
    private Resources ressources;
    private final List<Reservation> reservations;
    TextView tvDestination;
    TextView tvDate;
    TextView tvMontant;
    TextView tvStatut;

    public reservationAdapter(@NonNull Context context, int resource, List<Reservation>liste) {
        super(context, resource,liste);
        this.contexte = context;
        this.viewResourceId = resource;
        this.ressources = contexte.getResources();
        this.reservations = liste;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) contexte.getSystemService(Context.
                    LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(this.viewResourceId, parent, false);
        }

        Reservation reservation = reservations.get(position);
        if (reservation!=null){
            tvDestination = view.findViewById(R.id.tvDestinationRsv);
            tvDate = view.findViewById(R.id.tvDateRsv);
            tvMontant=view.findViewById(R.id.tvMontantRsv);
            tvStatut=view.findViewById(R.id.tvStatutRsv);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(reservation.getDate());  // Format de la date

            tvDestination.setText("Destination: "+reservation.getDestination());
            tvDate.setText("Date: "+formattedDate);
            tvMontant.setText("Montant payé: "+reservation.getMontant());
            tvStatut.setText("Statut: "+ reservation.getStatut());
        }
        return view;

    }
}
