package com.example.projet_makbal_mounir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class classementfiliere extends AppCompatActivity {
    ListView list;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classementfiliere);
        list = (ListView) findViewById(R.id.list);
        final Intent intention=getIntent();
        ArrayList<String> L = intention.getStringArrayListExtra("classementfil");


        ArrayAdapter<String> myadapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,L);
        list.setAdapter(myadapter);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
