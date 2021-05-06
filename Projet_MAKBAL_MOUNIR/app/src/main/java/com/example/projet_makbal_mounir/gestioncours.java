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
import java.util.Calendar;

public class gestioncours extends AppCompatActivity {
    EditText edt;
    String id;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestioncours);
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
        id=edt.getText().toString();
        Intent i=new Intent();
        i.setAction("modifier.cours");
        i.putExtra("idcours",id);
        startActivity(i);

    }

    public void SUPPRIMER(View view) {
        edt = (EditText) findViewById(R.id.edt);
        id=edt.getText().toString();
        new AlertDialog.Builder(view.getContext())
                .setTitle("Suppression Cours")
                .setMessage("Etes vous sures de vouloir supprimer ce cours")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new gestioncours.RetrieveFeedTask2(0).execute();



                    }
                }).setNegativeButton("No", null).show();


    }



    public class RetrieveFeedTask2 extends AsyncTask<Void, Void, Void> {
        private int req_code;

        public RetrieveFeedTask2(int req) {
            req_code = req;

        }


        @Override
        protected Void doInBackground(Void... voids) {
            if (req_code == 1) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conex = DriverManager.getConnection("jdbc:mysql://192.168.1.112:3306/BD?characterEncoding=latin1", "root", "mysql");

                    PreparedStatement prepare = conex.prepareStatement("INSERT INTO inscription VALUES(?,?,?,null)");
                    prepare.setInt(2, Integer.parseInt(id));
                    //prepare.setString(1, Matricule);
                    prepare.setInt(3, Calendar.getInstance().get(Calendar.YEAR));


                    prepare.executeUpdate();


                    conex.close();


                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                    Log.e("Dbtest", e.toString());

                }


            } else {

                try {
                    Log.i("TAG", id);


                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conex = DriverManager.getConnection("jdbc:mysql://192.168.1.112:3306/BD?characterEncoding=latin1", "root", "mysql");
                    String query1 = "delete from cours where Id = ?";
                    String query2 = "delete from inscription where Idcours = ?";


                    PreparedStatement preparedStmt = conex.prepareStatement(query1);
                    PreparedStatement preparedStmt2 = conex.prepareStatement(query2);
                    preparedStmt.setInt(1, Integer.parseInt(id));
                    preparedStmt2.setInt(1, Integer.parseInt(id));
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
