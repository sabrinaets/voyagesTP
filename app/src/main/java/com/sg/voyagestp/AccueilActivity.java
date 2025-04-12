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

import com.sg.voyagestp.modeles.Voyage;
import com.sg.voyagestp.modeles.voyageAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AccueilActivity extends AppCompatActivity {

    ListView listeVoyages;
    Button buttonHistorique;

    voyageAdapter adapteur;
    voyageViewModel viewModel;
    Spinner spinDestination;
    Spinner spinBudget;
    Spinner spinType;
    EditText eTDate;

    String destination = "";
    String budget="";
    String typeVoyage="";

    String dateVoyage="";
    String idUtilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.accueil_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        spinDestination = findViewById(R.id.spinDestination);
        spinBudget = findViewById(R.id.spinBudget);
        spinType= findViewById(R.id.spinTypeVoyage);
        eTDate = findViewById(R.id.eTDate);
        buttonHistorique = findViewById(R.id.buttonHistorique);
        idUtilisateur = getIntent().getStringExtra("idUtilisateur");
        if (idUtilisateur != null) {
            Log.d("DEBUG", "ID utilisateur connecté : " + idUtilisateur);
        }

        buttonHistorique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historique = new Intent (AccueilActivity.this,HistoriqueActivity.class);
                historique.putExtra("idUtilisateur", idUtilisateur);
                startActivity(historique);
            }
        });


        //Liste
        listeVoyages = findViewById(R.id.lvVoyages);
        adapteur = new voyageAdapter(this, R.layout.voyage_layout);
        listeVoyages.setAdapter(adapteur);

        //viewModel
        viewModel = new ViewModelProvider(this).get(voyageViewModel.class);
        viewModel.getVoyages().observe(this, voyages -> {
            adapteur.updateData(voyages);
            Toast.makeText(AccueilActivity.this, voyages.size() + " Voyages", Toast.LENGTH_SHORT).show();
        });
        // Observer les éventuelles erreurs
        viewModel.getError().observe(this, errorMessage ->
                Toast.makeText(AccueilActivity.this, errorMessage, Toast.LENGTH_SHORT).show()
        );

        if (spinDestination.getSelectedItem()!=null){
            destination = spinDestination.getSelectedItem().toString();
        }
        if (spinBudget.getSelectedItem()!=null) {
            budget = spinBudget.getSelectedItem().toString();
        }
        if (spinType.getSelectedItem()!=null) {
            typeVoyage = spinType.getSelectedItem().toString();
        }


        //mettre les valeurs des spinner
        Resources ressources = getResources();
        String[] destinations=ressources.getStringArray(R.array.destinations);
        String[] typesVoyage = ressources.getStringArray(R.array.types);
        String[] budgets = ressources.getStringArray(R.array.budgets);

        ArrayAdapter adapteurSpin1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1,destinations);
        ArrayAdapter adapteurSpin2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1,typesVoyage);
        ArrayAdapter adapteurSpin3 = new ArrayAdapter(this, android.R.layout.simple_list_item_1,budgets);

        spinDestination.setAdapter(adapteurSpin1);
        spinType.setAdapter(adapteurSpin2);
        spinBudget.setAdapter(adapteurSpin3);

        //Mettre des listerners pour chaque spinner afin de filtrer
        spinDestination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                destination = parent.getItemAtPosition(position).toString();

                viewModel.filtrer(destination, typeVoyage,eTDate.getText().toString(),budget);
                Log.d("Filtres", "destination=" + destination + " type=" + typeVoyage + " date=" + eTDate.getText().toString() + " budget=" + budget);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeVoyage = parent.getItemAtPosition(position).toString();
                viewModel.filtrer(destination, typeVoyage,eTDate.getText().toString(),budget);
                Log.d("Filtres", "destination=" + destination + " type=" + typeVoyage + " date=" + eTDate.getText().toString() + " budget=" + budget);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinBudget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                budget = parent.getItemAtPosition(position).toString();
                viewModel.filtrer(destination, typeVoyage,eTDate.getText().toString(),budget);
                Log.d("Filtres", "destination=" + destination + " type=" + typeVoyage + " date=" + eTDate.getText().toString() + " budget=" + budget);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Date
        eTDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dateVoyage = s.toString();
                viewModel.filtrer(destination,typeVoyage,dateVoyage,budget);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        //Détails voyage
        listeVoyages.setOnItemClickListener((adapterView, view, position, id) -> {
            Voyage voyage = adapteur.getItem(position);
            if (voyage!=null){
                Intent iVoirVoyage = new Intent(AccueilActivity.this,detailsVoyage.class);
                iVoirVoyage.putExtra("leVoyage",voyage);
                iVoirVoyage.putExtra("idUtilisateur",idUtilisateur);
                startActivity(iVoirVoyage);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        int r = checkSelfPermission("android.permission.INTERNET");
        if (r == PackageManager.PERMISSION_GRANTED) {
            if (viewModel.getAllVoyages().isEmpty()){
                viewModel.init(AccueilActivity.this);
            }
            else {
                List<Voyage> liste = viewModel.getAllVoyages();

            }
        } else {
            Toast.makeText(this, "Accès Internet non permis!", Toast.LENGTH_LONG).show();
        }
}
}