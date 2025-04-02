package com.sg.voyagestp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    EditText etCourriel;
    EditText etPassword;
    Button btnInscrire;
    Button btnConnecter;

    String courriel;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etCourriel = findViewById(R.id.editTextCourriel);
        etPassword = findViewById(R.id.editTextPassword);
        btnInscrire = findViewById(R.id.btnInscription);
        btnConnecter = findViewById(R.id.btnConnexion);

        btnConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lancer le thread qui va verifier le contenu du json
                String email = etCourriel.getText().toString();
                String password = etPassword.getText().toString();

                threadJSON thread = new threadJSON(MainActivity.this,email, password);
                thread.start();
            }
        });


        btnInscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Inscription.class);
                startActivity(intent);
            }
        });
    }
}

//npx json-server voyages.json