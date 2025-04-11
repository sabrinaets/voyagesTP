package com.sg.voyagestp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sg.voyagestp.modeles.Trip;
import com.sg.voyagestp.modeles.Voyage;
import com.sg.voyagestp.modeles.tripAdapter;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class detailsVoyage extends AppCompatActivity {

    TextView tvNom;
    ImageView img;

    TextView tvDest;

    TextView tvDesc;
    TextView jours;
    TextView prix;

    TextView type;
    TextView activites;

    ListView trips;

    List<Trip> listeTrips;

    Voyage voyage;
    tripAdapter adapteur;
    Button btnRetour;

    String idUtilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details_voyage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        idUtilisateur = getIntent().getStringExtra("idUtilisateur");
        tvNom = findViewById(R.id.tvNom);
        img = findViewById(R.id.tvImg);

        tvDest = findViewById(R.id.tvDest);

        tvDesc = findViewById(R.id.tvDesc);
        jours = findViewById(R.id.tvJours);
        prix = findViewById(R.id.tvPrix);

        type = findViewById(R.id.tvType);
        activites = findViewById(R.id.tvActivites);

        trips = findViewById(R.id.lvTrips);

        voyage = (Voyage) getIntent().getSerializableExtra("leVoyage");

        if (voyage!=null) {
            tvNom.setText(voyage.getNom_voyage());
            Picasso.get().load(voyage.getImage_url()).into(img);
            tvDest.setText(voyage.getDestination());
            tvDesc.setText(voyage.getDescription());
            jours.setText("Nombre de jours: "+String.valueOf(voyage.getDuree_jours()));
            prix.setText(String.valueOf(voyage.getPrix())+"$");
            type.setText("Type: "+voyage.getType_de_voyage());
            activites.setText("Activités incluses: "+voyage.getActivites_incluses());
            listeTrips = Arrays.asList(voyage.getTrips());

            adapteur = new tripAdapter(this,R.layout.trip_layout,listeTrips);
            trips.setAdapter(adapteur);

        }

        else {
            Log.e("Intent","Erreur de transmission du voyage");
        }


        btnRetour = findViewById(R.id.btnRetour);

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retour = new Intent (detailsVoyage.this,AccueilActivity.class);
                retour.putExtra("idUtilisateur",idUtilisateur);
                startActivity(retour);
            }
        });

        //Détails voyage
        trips.setOnItemClickListener((adapterView, view, position, id) -> {
            Trip trip = adapteur.getItem(position);
            if (trip!=null){
                if(trip.getNb_places_disponibles()>0) {
                    Intent iReserverTrip = new Intent(detailsVoyage.this, ReserverActivity.class);
                    iReserverTrip.putExtra("leVoyage", voyage);
                    iReserverTrip.putExtra("leTrip", trip);
                    iReserverTrip.putExtra("idUtilisateur",idUtilisateur);
                    startActivity(iReserverTrip);
                }
                else {
                    new AlertDialog.Builder(this)
                            .setTitle("Alert")
                            .setMessage("Il n'y a aucune place disponible pour ce voyage")
                            .setNegativeButton("Fermer", (dialog, which) -> {
                                // Just dismiss the dialog
                                dialog.dismiss();
                            })
                            .show();
                }
            }
        });
    }
}