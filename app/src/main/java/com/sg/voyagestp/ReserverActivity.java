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
import java.util.List;

public class ReserverActivity extends AppCompatActivity {

    ListView listeVoyages;

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

}
}