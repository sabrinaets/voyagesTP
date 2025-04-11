package com.sg.voyagestp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.sg.voyagestp.modeles.Client;
import com.sg.voyagestp.modeles.Trip;
import com.sg.voyagestp.modeles.Voyage;
import com.sg.voyagestp.modeles.voyageAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ReserverActivity extends AppCompatActivity {

    Voyage voyage;
    Trip trip;
    String idUtilisateur;
    TextView nomVoyage;
    TextView dateVoyage;
    EditText nombrePersonne;
    TextView montant;
    Button reserver;
    Button retour;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.reserver_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        voyage = (Voyage) getIntent().getSerializableExtra("leVoyage");
        trip = (Trip) getIntent().getSerializableExtra("leTrip");
        idUtilisateur = getIntent().getStringExtra("idUtilisateur");
        nomVoyage = findViewById(R.id.tvNomVoyageRsv);
        dateVoyage = findViewById(R.id.tvDateTripRsv);
        nombrePersonne = findViewById(R.id.eTNbPersonne);
        montant = findViewById(R.id.tvPrixAPayer);
        reserver = findViewById(R.id.ConfirmerRsv);
        retour = findViewById(R.id.buttonRetourDetail);


        montant.setText("Prix: 0.0$");
        nomVoyage.setText(voyage.getNom_voyage());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(trip.getDate());  // Format de la date
        dateVoyage.setText("Date: "+formattedDate);


        nombrePersonne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int nbPersonnes = Integer.parseInt(s.toString());
                    double total = voyage.getPrix() * nbPersonnes;
                    montant.setText("Prix: " + total + "$");
                } catch (NumberFormatException e) {
                    montant.setText("Prix: 0.0$");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retour = new Intent (ReserverActivity.this,detailsVoyage.class);
                retour.putExtra("idUtilisateur",idUtilisateur);
                retour.putExtra("leVoyage",voyage);
                startActivity(retour);
            }
        });
}
}