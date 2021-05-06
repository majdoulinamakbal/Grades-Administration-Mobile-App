package com.example.projet_makbal_mounir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class Etudiants extends AppCompatActivity {
    ListView list;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiants);
        list = (ListView) findViewById(R.id.list);
        final Intent intention=getIntent();
        String L[]= intention.getStringArrayExtra("matricule");


        ArrayAdapter<String> myadapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,L);
        list.setAdapter(myadapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);

                // Display the selected item text on TextView
                Intent i=new Intent();
                i.setAction("etudiant.detail");
                i.putExtra("matricule",selectedItem);
                startActivity(i);

            }
        });
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    }

