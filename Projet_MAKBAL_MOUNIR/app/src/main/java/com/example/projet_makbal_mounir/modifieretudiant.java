package com.example.projet_makbal_mounir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class modifieretudiant extends AppCompatActivity {
    String matricule;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifieretudiant);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    public void ville(View view) {
        Intent intention = getIntent();
        matricule = intention.getStringExtra("matricule");
        Intent i=new Intent();
        i.setAction("modifier.ville");
        i.putExtra("matricule",matricule);
        startActivity(i);
    }
}
