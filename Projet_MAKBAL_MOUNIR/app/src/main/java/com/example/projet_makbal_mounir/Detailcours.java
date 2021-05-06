package com.example.projet_makbal_mounir;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Detailcours extends AppCompatActivity {
    TextView tv1;
    TextView tvid;
    TextView tvsem;
    TextView tvfil;
    TextView tvcoef;
    String id;
    String semestre;
    int coeff;
    String filiere;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailcours);
        tvid = (TextView) findViewById(R.id.tvid);
        tvsem = (TextView) findViewById(R.id.tvsem);
        tvfil = (TextView) findViewById(R.id.tvfil);
        tvcoef = (TextView)findViewById(R.id.tvcoeff);
        new RetrieveFeedTask().execute(tvid,tvsem,tvfil,tvcoef);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void Inscrire(View view) {
        Intent intention = getIntent();
        String s = intention.getStringExtra("cours");
        Intent i=new Intent();
        i.setAction("detailscours.inscrire");
        i.putExtra("id", s);
        i.putExtra("filière", tvfil.getText().toString());

        startActivity(i);


    }

    public class RetrieveFeedTask extends AsyncTask<TextView, Void, Void> {
        private Activity activity;

        @Override
        protected Void doInBackground(TextView... textViews) {
            try {
                Intent intention = getIntent();
                String s = intention.getStringExtra("cours");
                int idd=Integer.parseInt(s);
                //tv1 = (TextView) findViewById(R.id.tv1);
                //tv1.setText("Description du cours " + s);
                Class.forName("com.mysql.jdbc.Driver");
                Connection conex = DriverManager.getConnection("jdbc:mysql://192.168.1.112:3306/BD?characterEncoding=latin1", "root", "mysql");
                String sql1 = "select * from cours where id ='" + idd + "'";




                PreparedStatement prest = conex.prepareStatement(sql1);
                ResultSet rs = prest.executeQuery();





                while (rs.next()) {
                    id = rs.getString(2);//titre

                    semestre = rs.getString(4);
                    coeff = rs.getInt(3);
                    filiere = rs.getString(5);







                }

                conex.close();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                Log.e("Dbtest", e.toString());

            }

            return null;
        }
        @Override



        protected void onPostExecute(Void aVoid) {
            Log.e("Dbtest", String.valueOf(id));


            tvid.setText(id);


            tvsem.setText(semestre);
            tvcoef.setText(String.valueOf(coeff));
            tvfil.setText(filiere);
        }
    }


}











