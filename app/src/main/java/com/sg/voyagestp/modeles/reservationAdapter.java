package com.sg.voyagestp.modeles;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sg.voyagestp.HistoriqueActivity;
import com.sg.voyagestp.R;
import com.sg.voyagestp.ReserverActivity;
import com.sg.voyagestp.threadJsonMajPlaces;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class reservationAdapter extends ArrayAdapter<Reservation> {
    private Context contexte;
    private int viewResourceId;
    private Resources ressources;
    private final List<Reservation> reservations;
    TextView tvDestination;
    TextView tvDate;
    TextView tvMontant;
    TextView tvStatut;
    Button btnAnnuler;

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
            btnAnnuler = view.findViewById(R.id.buttonAnnulerRsv);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            String formattedDate = sdf.format(reservation.getDate());  // Format de la date
            Log.i("Allo destiantion peut etre", reservation.getDestination());
            tvDestination.setText("Destination: "+reservation.getDestination());
            tvDate.setText("Date: "+formattedDate);
            tvMontant.setText("Montant payé: "+reservation.getMontant());
            String statutText = reservation.getStatut() == 1 ? "Confirmée" : "Annulée";
            tvStatut.setText("Statut: " + statutText);

            if (reservation.getStatut() == 1) {
                btnAnnuler.setVisibility(View.VISIBLE);
                btnAnnuler.setOnClickListener(v -> {
                    ReservationDAO reservationDAO = new ReservationDAO(contexte);
                    reservationDAO.open();
                    reservationDAO.updateStatut(reservation.getId(), 0);
                    reservation.setStatut(0);
                    reservationDAO.close();

                    new threadJsonMajPlaces(contexte, Integer.parseInt(reservation.getIdVoyage()), formattedDate, -reservation.getNombre_personne()).start();


                    notifyDataSetChanged();

                    Toast.makeText(contexte, "Réservation annulée", Toast.LENGTH_SHORT).show();
                });
            } else {
                btnAnnuler.setVisibility(View.GONE);
            }
        }
        return view;

    }
}
