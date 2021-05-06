package com.example.projet_makbal_mounir;

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
import java.sql.SQLException;

public class modifiercoeff extends AppCompatActivity {
    EditText edt;
    String coeffid;
    String idcours;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifiercoeff);
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
        coeffid = edt.getText().toString();
        new modifiercoeff.ConnectDBtask2(0).execute();
        Toast.makeText(this, "Coefficient  Modifié", Toast.LENGTH_SHORT).show();
    }

    public class ConnectDBtask2 extends AsyncTask<Void, Void, Void> {
        private int req_code;

        public ConnectDBtask2(int req) {
            req_code = req;

        }


        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");

                Connection conex = DriverManager.getConnection("jdbc:mysql://192.168.1.112:3306/BD?characterEncoding=latin1", "root", "mysql");
                if (req_code == 0) { //driver
                    Intent intention = getIntent();
                    idcours = intention.getStringExtra("idcours");
                    Log.i("hi", coeffid);

                    PreparedStatement prepare = conex.prepareStatement("UPDATE cours " +
                            "SET coeff = ? " +
                            "WHERE id = ?");

                    prepare.setInt(2, Integer.parseInt(idcours));
                    //prepare.setString(1, Matricule);
                    prepare.setInt(1, Integer.parseInt(coeffid));
                    Log.i("hi", prepare.toString());


                    prepare.executeUpdate();


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


