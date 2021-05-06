package com.example.projet_makbal_mounir;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class gestionetudiant extends AppCompatActivity {
    EditText edt;
    String matricule;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionetudiant);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void MODIFIER(View view) {
        edt = (EditText) findViewById(R.id.edt);
        matricule=edt.getText().toString();
        Intent i=new Intent();
        i.setAction("modifier.etudiant");
        i.putExtra("matricule",matricule);
        startActivity(i);

    }

    public void SUPPRIMER(View view) {
        edt = (EditText) findViewById(R.id.edt);
        matricule=edt.getText().toString();
        new AlertDialog.Builder(view.getContext())
                .setTitle("Suppression Etudiant")
                .setMessage("Etes vous sures de vouloir supprimer cet Etudiant")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new gestionetudiant.RetrieveFeedTask3(0).execute();



                    }
                }).setNegativeButton("No", null).show();


    }



    public class RetrieveFeedTask3 extends AsyncTask<Void, Void, Void> {
        private int req_code;

        public RetrieveFeedTask3(int req) {
            req_code = req;

        }


        @Override
        protected Void doInBackground(Void... voids) {



             if (req_code==0) {

                try {
                    Log.i("TAG", matricule);


                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conex = DriverManager.getConnection("jdbc:mysql://192.168.1.112:3306/BD?characterEncoding=latin1", "root", "mysql");
                    String query1 = "delete from etudiant where Matricule = ?";
                    String query2 = "delete from inscription where Matricule = ?";


                    PreparedStatement preparedStmt = conex.prepareStatement(query1);
                    PreparedStatement preparedStmt2 = conex.prepareStatement(query2);
                    preparedStmt.setString(1, matricule);
                    preparedStmt2.setString(1, matricule);
                    preparedStmt.execute();
                    preparedStmt2.execute();


                    conex.close();


                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.e("TAG", e.toString());
                }

            }
            return null;
        }
    }
}
