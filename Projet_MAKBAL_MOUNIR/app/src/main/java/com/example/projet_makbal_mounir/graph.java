package com.example.projet_makbal_mounir;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class graph extends AppCompatActivity {
    BarChart barChart;
    ArrayList<String> labels;
    BarDataSet bardataset;
    ArrayList<BarEntry> entries;
    Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        barChart = findViewById(R.id.BarChart);
        new graph.RetrieveFeedTask3(1).execute(barChart);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





    }
    public class RetrieveFeedTask3 extends AsyncTask<BarChart, Void, Void> {
        private int req_code;

        public RetrieveFeedTask3(int req) {
            req_code = req;

        }


        @Override
        protected Void doInBackground(BarChart... barCharts) {
            if (req_code == 1) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conex = DriverManager.getConnection("jdbc:mysql://192.168.1.112:3306/BD?characterEncoding=latin1", "root", "mysql");

                    String sql1 = "select titre, I.Idcours, avg(note) from inscription I,cours C  where I.Idcours=C.Id group by titre;";
                    PreparedStatement prest1 = conex.prepareStatement(sql1);
                    ResultSet rs1 = prest1.executeQuery();
                    entries = new ArrayList<>();
                    labels = new ArrayList<String>();


                    int i=0;
                    while (rs1.next()) {

                        entries.add(new BarEntry(rs1.getInt(3), i));
                        labels.add(rs1.getString(2));
                        i++;










                    }


                    /**/


                    conex.close();


                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                    Log.e("Dbtest", e.toString());

                }


            }




            return null;
        }

    protected void onPostExecute(Void aVoid) {
        //Log.e("Dbtest", String.valueOf(id));
        BarDataSet bardataset = new BarDataSet(entries, "moyennes");
        BarData data = new BarData(labels,bardataset);
        barChart.setData(data); // set the data and list of labels into chart
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.animateY(5000);


    }
    }
    }




