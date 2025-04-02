package com.sg.voyagestp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sg.voyagestp.modeles.Client;

public class Inscription extends AppCompatActivity {

    EditText nNom;
    EditText nPrenom;
    EditText nEmail;
    EditText nPassword;
    EditText nAge;
    EditText nTelephone;
    EditText nAdresse;

    Button btnConfirmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.inscription_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        nNom = findViewById(R.id.editNom);
        nPrenom=findViewById(R.id.editPrenom);
        nEmail = findViewById(R.id.editEmail);
        nPassword = findViewById(R.id.editPassword);
        nAge = findViewById(R.id.editAge);
        nTelephone=findViewById(R.id.editPhone);
        nAdresse = findViewById(R.id.editAdresse);
        btnConfirmer = findViewById(R.id.btnEnregistrer);


        btnConfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nouvNom = nNom.getText().toString();
                String nouvPrenom = nPrenom.getText().toString();
                String nouvEmail = nEmail.getText().toString();
                int nouvAge = Integer.parseInt(nAge.getText().toString());
                String nouvPassword = nPassword.getText().toString();
                String nouvTelephone = nTelephone.getText().toString();
                String nouvAdresse= nAdresse.getText().toString();

                Client nouvClient = new Client(nouvNom,nouvPrenom,nouvAge,nouvEmail,nouvTelephone,nouvAdresse,nouvPassword);

                threadJsonInscription thread = new threadJsonInscription(Inscription.this,nouvClient);
                thread.start();
            }
        });

    }
}