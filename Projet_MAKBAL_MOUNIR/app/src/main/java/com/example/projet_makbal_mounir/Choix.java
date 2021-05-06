package com.example.projet_makbal_mounir;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Choix extends AppCompatActivity {
    TextView bienvenu;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix);
        bienvenu=(TextView)findViewById(R.id.bienvenu);

        Intent intention=getIntent();
        String s=intention.getStringExtra("nomprof");

        bienvenu.setText("Bienvenue Professeur "+ s);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
    });}




    public void listecours(View view) {
        new ConnectDBtask(0).execute();
    }
    public void listeudiants(View view) {
        new ConnectDBtask(1).execute();

    }
    public void gestioncours(View view) {
        Intent i=new Intent();
        i.setAction("gestion.cours");
        startActivity(i);

    }
    public void gestionetudiant(View view) {
        Intent i=new Intent();
        i.setAction("gestion.etudiant");
        startActivity(i);
    }

    public void graphe(View view) {
        Intent i=new Intent();
        i.setAction("graphe");
        startActivity(i);
    }

    public void classement(View view) {
        Intent i=new Intent();
        i.setAction("classement");
        startActivity(i);
    }


    public class ConnectDBtask extends AsyncTask<Void, Void, Void> {
        private int req_code;

        public ConnectDBtask(int req) {
            req_code = req;

        }


        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Class.forName("com.mysql.jdbc.Driver");

                Connection conex=DriverManager.getConnection("jdbc:mysql://192.168.1.112:3306/BD?characterEncoding=latin1","root","mysql");
            if (req_code == 0) { //driver
                Integer nom;
                int id;
                List<String> L = new ArrayList<String>();
                String sql1 = "Select * from cours;";
                PreparedStatement prest = conex.prepareStatement(sql1);
                ResultSet rs = prest.executeQuery();//Résultat
                while (rs.next()){
                        nom = rs.getInt(1);


                        L.add(Integer.toString(nom));





                    }
                    String str[] = new String[L.size()];

                    for (int j = 0; j < L.size(); j++) {

                        // Assign each value to String array
                        str[j] = L.get(j);

                    }



                    Intent i=new Intent();
                    i.setAction("liste.cours");
                    i.putExtra("titre", str);//retourner les noms de cours

                    startActivity(i);
                    conex.close();


                }
            else if (req_code == 1) {
                String Matricule;
                List<String> L = new ArrayList<String>();
                String sql1 = "Select * from Etudiant;";
                PreparedStatement prest = conex.prepareStatement(sql1);
                ResultSet rs = prest.executeQuery();

                while (rs.next()){
                    Matricule = rs.getString(1);


                    L.add(Matricule);





                }
                String str[] = new String[L.size()];

                for (int j = 0; j < L.size(); j++) {

                    // Assign each value to String array
                    str[j] = L.get(j);

                }




                Intent i=new Intent();
                i.setAction("liste.etudiant");
                i.putExtra("matricule", str);//retourner les noms de cours

                startActivity(i);
                conex.close();


            }


        } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }






}
