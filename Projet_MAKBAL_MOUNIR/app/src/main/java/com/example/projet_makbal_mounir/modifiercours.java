package com.example.projet_makbal_mounir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class modifiercours extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifiercours);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void semestre(View view) {
        Intent intention = getIntent();
        String id = intention.getStringExtra("idcours");
        Intent i=new Intent();
        i.setAction("modifier.semestre");
        i.putExtra("idcours",id);
        startActivity(i);
    }

    public void COEFFICIENT(View view) {
        Intent intention = getIntent();
        String id = intention.getStringExtra("idcours");
        Intent i=new Intent();
        i.setAction("modifier.coefficient");
        i.putExtra("idcours",id);
        startActivity(i);
    }
}
