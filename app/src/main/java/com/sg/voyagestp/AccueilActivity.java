package com.sg.voyagestp;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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

public class AccueilActivity extends AppCompatActivity {

    ListView listeVoyages;

    voyageAdapter adapteur;
    voyageViewModel viewModel;
    Spinner spinDestination;
    Spinner spinBudget;
    Spinner spinType;
    EditText eTDate;

    String destination;
    double budget;
    String typeVoyage;

    Date dateVoyage;

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

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        spinDestination = findViewById(R.id.spinDestination);
        spinBudget = findViewById(R.id.spinBudget);
        spinType= findViewById(R.id.spinTypeVoyage);
        eTDate = findViewById(R.id.eTDate);



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
            budget = Double.parseDouble(spinBudget.getSelectedItem().toString());
        }
        if (spinType.getSelectedItem()!=null) {
            typeVoyage = spinType.getSelectedItem().toString();
        }

        //essayer davoir la valeur exacte de date
        try {
            dateVoyage = formatter.parse(eTDate.getText().toString());
        }
        catch(java.text.ParseException e){
            Log.i("Date", e.toString());
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