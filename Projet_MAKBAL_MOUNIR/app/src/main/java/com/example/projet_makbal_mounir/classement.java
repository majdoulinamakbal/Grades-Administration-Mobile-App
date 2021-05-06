package com.example.projet_makbal_mounir;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class classement extends AppCompatActivity {
    EditText edt;
    String fil;
    ArrayList<String> L;
    ListView list;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classement);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void go(View view) {
        edt=(EditText) findViewById(R.id.edt);
        fil=edt.getText().toString();
        new classement.RetrieveFeedTask3().execute(list);
    }
    public class RetrieveFeedTask3 extends AsyncTask<ListView, Void, Void> {


        @Override
        protected Void doInBackground(ListView... textViews) {
            try {

                Class.forName("com.mysql.jdbc.Driver");
                Connection conex = DriverManager.getConnection("jdbc:mysql://192.168.1.112:3306/BD?characterEncoding=latin1", "root", "mysql");


                //tv1 = (TextView) findViewById(R.id.tv1);

                String sql1 = "select matricule,nom,prenom,moy from moyenne where filière= '" + fil + "' order by moy desc";
                PreparedStatement prest1 = conex.prepareStatement(sql1);
                ResultSet rs1 = prest1.executeQuery();


                L = new ArrayList<String>();
                String nom;
                String prenom;
                String mat;
                int moy;
                int i=1;
                while (rs1.next()) {
                    mat=rs1.getString(1);
                    nom = rs1.getString(2);
                    prenom=rs1.getString(3);
                    moy=rs1.getInt(4);





                    //

                    L.add(mat+" | "+Integer.toString(i)+ " | " + nom + "  " + prenom + " |" + Integer.toString(moy));
                    i++;


                }
                Log.i("TAG", L.toString());



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
            //ArrayAdapter adapter = new ArrayAdapter(classement.this, android.R.layout.simple_list_item_1, L);

            //list.setAdapter(adapter);
            Intent i=new Intent();
            i.setAction("classement.fil");
            i.putStringArrayListExtra("classementfil",L);
            startActivity(i);



        }
    }


}
