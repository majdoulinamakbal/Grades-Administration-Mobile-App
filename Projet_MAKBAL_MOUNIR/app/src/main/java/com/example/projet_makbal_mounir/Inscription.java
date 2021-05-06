package com.example.projet_makbal_mounir;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class Inscription extends AppCompatActivity {
    EditText edt;
    String Matricule;
    Button button;
    String fil;
    Integer test;
    String filière;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void OK(View view) {

        edt = (EditText) findViewById(R.id.edt);
        Matricule = edt.getText().toString();
        new RetrieveFeedTask1(1).execute();


    }

    public void Annuler(View view) {
        /**Intent i=new Intent();
         i.setAction("inscrire.annuler");
         startActivity(i);**/

        new AlertDialog.Builder(view.getContext())
                .setTitle("Annuler l'inscription")
                .setMessage("Etes vous sures de vouloir annuler l'inscription")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new RetrieveFeedTask1(0).execute();


                    }
                }).setNegativeButton("No", null).show();

    }

    private class RetrieveFeedTask1 extends AsyncTask<Void, Integer, Void> {
        private Activity activity;
        private int req_code;

        public RetrieveFeedTask1(int req) {
            req_code = req;

        }


        @Override
        protected Void doInBackground(Void...voids) {
            if (req_code == 1) {
                try {


                    Intent intention = getIntent();
                    String id = intention.getStringExtra("id");
                    filière = intention.getStringExtra("filière");
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conex = DriverManager.getConnection("jdbc:mysql://192.168.1.112:3306/BD?characterEncoding=latin1", "root", "mysql");
                    PreparedStatement prepare2 = conex.prepareStatement("select * from inscription where idcours=? and matricule=?");
                    PreparedStatement prepare = conex.prepareStatement("INSERT INTO inscription VALUES(?,?,?,null)");
                    PreparedStatement prepare1 = conex.prepareStatement("select filière from moyenne where matricule=?");
                    prepare2.setInt(1, Integer.parseInt(id));
                    prepare2.setString(2, Matricule);
                    prepare.setInt(2, Integer.parseInt(id));
                    prepare.setString(1, Matricule);
                    prepare.setInt(3, Calendar.getInstance().get(Calendar.YEAR));
                    prepare1.setString(1, Matricule);

                    ResultSet rs = prepare1.executeQuery();
                    ResultSet rs1 = prepare2.executeQuery();
                    Log.i("tester", filière);
                    while (rs.next()) {
                        fil = rs.getString(1);// Relever la filière de l'etudiant
                    }


                    if (rs.next() == false) {
                        prepare.execute();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                        Toast.makeText(getApplicationContext(), "Etudiant ajouté", Toast.LENGTH_SHORT).show();}
                        });
                    } else {

                        if (fil.equals(filière) && rs1.next()==false) {
                            prepare.execute();
                            runOnUiThread(new Runnable() {
                                              @Override
                                              public void run() {
                            Toast.makeText(getApplicationContext(), "Etudiant ajouté", Toast.LENGTH_SHORT).show();}});
                        }




                    }


                    conex.close();


                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            } else {

                try {
                    Intent intention = getIntent();
                    String id = intention.getStringExtra("id");
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conex = DriverManager.getConnection("jdbc:mysql://192.168.1.112:3306/BD?characterEncoding=latin1", "root", "mysql");
                    String query = "delete from Inscription where Idcours = ? and Matricule=?";
                    PreparedStatement preparedStmt = conex.prepareStatement(query);
                    preparedStmt.setInt(1, Integer.parseInt(id));
                    preparedStmt.setString(2, Matricule);
                    preparedStmt.execute();
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








