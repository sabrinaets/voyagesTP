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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.sg.voyagestp.modeles.Reservation;
import com.sg.voyagestp.modeles.ReservationDAO;
import com.sg.voyagestp.modeles.Trip;
import com.sg.voyagestp.modeles.Voyage;
import com.sg.voyagestp.modeles.reservationAdapter;
import com.sg.voyagestp.modeles.voyageAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class HistoriqueActivity extends AppCompatActivity {

    String idUtilisateur;
    ListView listeReservation;
    List<Reservation> listeRsv;
    reservationAdapter adapteur;
    Button bouttonRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.historique_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        idUtilisateur = getIntent().getStringExtra("idUtilisateur");
        bouttonRetour = findViewById(R.id.buttonRetourHistorique);
        listeReservation = findViewById(R.id.lvReservation);

        ReservationDAO dao = new ReservationDAO(this);
        dao.open();
        listeRsv = dao.getReservationsParClient(idUtilisateur);
        dao.close();

        adapteur = new reservationAdapter(this, R.layout.reservation_layout, listeRsv);
        listeReservation.setAdapter(adapteur);


        bouttonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retour = new Intent (HistoriqueActivity.this,AccueilActivity.class);
                retour.putExtra("idUtilisateur",idUtilisateur);
                startActivity(retour);
            }
        });
}
}