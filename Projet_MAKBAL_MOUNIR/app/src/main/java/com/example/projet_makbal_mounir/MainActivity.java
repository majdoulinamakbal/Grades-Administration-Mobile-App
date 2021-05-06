package com.example.projet_makbal_mounir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    EditText name;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Login(View view)  {
        Scanner scan;
        name=(EditText)findViewById(R.id.name);
        password=(EditText)findViewById(R.id.password);
        String nom=name.getText().toString();
        String mdp=password.getText().toString();
        scan = new Scanner(getResources().openRawResource(R.raw.pwd));
        int j=0;
        while (scan.hasNextLine()) {
            String data = scan.nextLine();
            String[] strArray = data.split(",");

            if (strArray[0].equals(nom) && strArray[1].equals(mdp)){
                j++;
                scan.close();
                Intent i=new Intent();
                i.setAction("prof.choix");
                i.putExtra("nomprof",nom);
                startActivity(i);
                break;


            }





        }

        scan.close();


        if(j==0){
           Toast.makeText(this, "Logon Denied !", Toast.LENGTH_SHORT).show();

    }
}

}
