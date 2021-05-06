package com.example.projet_makbal_mounir;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Courssuiviesparetudiant extends AppCompatActivity {
    ListView list;
    TextView tv1;
    List<String> L;
    String mat;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courssuiviesparetudiant);
        list = (ListView) findViewById(R.id.list);
        tv1=(TextView)findViewById(R.id.tv1);

        Intent intention=getIntent();
        mat=intention.getStringExtra("Matricule");
        tv1.setText("Cours suivis par \n"+mat);


        new Courssuiviesparetudiant.RetrieveFeedTask2().execute(list);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





    }
    public class RetrieveFeedTask2 extends AsyncTask<ListView, Void, Void> {


        @Override
        protected Void doInBackground(ListView... textViews) {
            try {
                Intent intention=getIntent();
                mat=intention.getStringExtra("Matricule");
                Class.forName("com.mysql.jdbc.Driver");
                Connection conex = DriverManager.getConnection("jdbc:mysql://192.168.1.112:3306/BD?characterEncoding=latin1", "root", "mysql");


                //tv1 = (TextView) findViewById(R.id.tv1);

                String sql1 = "select I.Idcours,C.titre,coeff,filière,note from inscription I,cours C where I.Idcours=C.Id and I.matricule= '" + mat+ "'";
                PreparedStatement prest1 = conex.prepareStatement(sql1);
                ResultSet rs1 = prest1.executeQuery();

                L = new ArrayList<String>();
                String nomc;
                String numc;
                String coeff;
                String filiere;
                String note;
                while (rs1.next()) {
                    numc = rs1.getString(1);
                    nomc=rs1.getString(2);
                    coeff=rs1.getString(3);
                    filiere=rs1.getString(4);
                    note=rs1.getString(5);




                    Log.i("TAG", nomc);

                    L.add("Titre de la matière :"+" " + nomc+ "\n" +"coefficient :"+" "+coeff+ "\n" +"filiere"+" "+filiere+"\n"+"note"+" "+note);


                }



            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }















        @Override



        protected void onPostExecute(Void aVoid) {
            //Log.e("Dbtest", String.valueOf(id));
            ArrayAdapter adapter = new ArrayAdapter(Courssuiviesparetudiant.this, android.R.layout.simple_list_item_1, L);

            list.setAdapter(adapter);



        }
    }

}
