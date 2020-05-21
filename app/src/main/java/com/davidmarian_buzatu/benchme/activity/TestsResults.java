package com.davidmarian_buzatu.benchme.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.davidmarian_buzatu.benchme.R;
import com.davidmarian_buzatu.benchme.model.Device;
import com.google.gson.Gson;

public class TestsResults extends AppCompatActivity {
    private Device device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_results);

        device = new Gson().fromJson(getIntent().getStringExtra("Device"), Device.class);
        setInfoInView();
    }

    private void setInfoInView() {
        TextView cpuTV1 = findViewById(R.id.act_fullResults_TV_CPU_Test_1);
        TextView cpuTV2 = findViewById(R.id.act_fullResults_TV_CPU_Test_2);
        TextView cpuTV3 = findViewById(R.id.act_fullResults_TV_CPU_Test_3);
        TextView cpuTV4 = findViewById(R.id.act_fullResults_TV_CPU_Test_4);

        TextView hddTV1 = findViewById(R.id.act_fullResults_TV_HDD_Test_1);
        TextView hddRV2 = findViewById(R.id.act_fullResults_TV_HDD_Test_2);

        // Here we should put write and read speed for HDD

        TextView ramTV1 = findViewById(R.id.act_fullResults_TV_RAM_Test_1);

        // Here we should pur write and read speed for RAM;

        cpuTV1.setText("Mersenne: " + device.getScoreMersenne());
        cpuTV2.setText("Roots: " + device.getScoreRoots());
        cpuTV3.setText("Hash: " + device.getScoreHASH());
        cpuTV4.setText("Sieve Of Atkin: " + device.getScoreAtkin());

    }
}
