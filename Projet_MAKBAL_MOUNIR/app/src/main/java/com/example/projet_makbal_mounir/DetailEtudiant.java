package com.example.projet_makbal_mounir;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetailEtudiant extends AppCompatActivity {
    TextView tv1;
    TextView tvmat;
    TextView tvnom;
    TextView tvprenom;
    TextView tvville;
    TextView tvdate;
    TextView tvmoy;
    String mat;
    String nom;
    String prenom;
    String ville;
    int moy;
    Date date;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_etudiant);
        tv1 = (TextView) findViewById(R.id.tv1);
        tvmat = (TextView) findViewById(R.id.tvmat);
        tvnom = (TextView) findViewById(R.id.tvnom);
        tvprenom = (TextView)findViewById(R.id.tvprenom);
        tvville= (TextView)findViewById(R.id.tvville);
        tvdate=(TextView)findViewById(R.id.tvdate);
        tvmoy=(TextView)findViewById(R.id.tvmoy);
        Intent intention = getIntent();
        String s = intention.getStringExtra("matricule");
        tv1 = (TextView) findViewById(R.id.tv1);
        tv1.setText("Description de " + s);
        new DetailEtudiant.RetrieveFeedTask1(1).execute(tvmat,tvnom,tvprenom,tvdate,tvmoy);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void Courssuivis(View view) {
        Intent intention = getIntent();
        String s = intention.getStringExtra("matricule");

        Intent i=new Intent();
        i.setAction("liste.cours.etudiant");


        i.putExtra("Matricule", s);//retourner les noms de cours

        startActivity(i);


    }
    public class RetrieveFeedTask1 extends AsyncTask<TextView, Void, Void> {
        private int req_code;

        public RetrieveFeedTask1(int req) {
            req_code = req;

        }

        @Override
        protected Void doInBackground(TextView... textViews) {
            try {
                Intent intention = getIntent();
                String s = intention.getStringExtra("matricule");
                Class.forName("com.mysql.jdbc.Driver");
                Connection conex = DriverManager.getConnection("jdbc:mysql://192.168.1.112:3306/BD?characterEncoding=latin1", "root", "mysql");
                if(req_code==1){
                Log.i("matricule", s);


                String sql1 = "select * from Etudiant where Matricule ='" + s + "'";
                Log.i("sql1", sql1);
                String sql2="select (sum(note*coeff)/sum(coeff)) from inscription I,cours C where I.Idcours=C.Id and I.matricule= '" + s + "'";
                Log.i("sql2", sql2);




                PreparedStatement prest1 = conex.prepareStatement(sql1);
                PreparedStatement prest2 = conex.prepareStatement(sql2);
                ResultSet rs1 = prest1.executeQuery();
                ResultSet rs2= prest2.executeQuery();





                while (rs1.next()) {
                    mat = rs1.getString(1);
                    nom = rs1.getString(2);
                    prenom = rs1.getString(3);
                    ville=rs1.getString(4);
                    date = rs1.getDate(5);







                }
                while (rs2.next()) {
                    moy = rs2.getInt(1);








                }

                conex.close();}

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                Log.e("Dbtest", e.toString());

            }

            return null;
        }
        @Override



        protected void onPostExecute(Void aVoid) {
            //Log.e("Dbtest", String.valueOf(id));


            tvmat.setText(mat);
            tvnom.setText(nom);
            tvprenom.setText(prenom);
            tvdate.setText(date.toString());
            tvville.setText(ville);
            tvmoy.setText(String.valueOf(moy));
        }
    }


}




