package com.sg.voyagestp;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccueilActivity extends AppCompatActivity {

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

        destination = spinDestination.getSelectedItem().toString();
        budget =Double.parseDouble(spinBudget.getSelectedItem().toString());
        typeVoyage =spinType.getSelectedItem().toString();

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
}