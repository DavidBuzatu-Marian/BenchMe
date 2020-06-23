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
        setCPUTV();
        setHDDTV();
        setRAMTV();
    }


    private void setRAMTV() {
        TextView ramHTV1 = findViewById(R.id.act_fullResults_TV_RAM_Test_1_header);
        TextView ramTV1 = findViewById(R.id.act_fullResults_TV_RAM_Test_1);
        ramHTV1.setText("RAM score: ");
        ramTV1.setText(new StringBuilder().append(String.format("%.2f",device.getScoreRAM())));
    }

    private void setHDDTV() {
        TextView hddHTV1 = findViewById(R.id.act_fullResults_TV_HDD_Test_1_header);
        TextView hddHTV2 = findViewById(R.id.act_fullResults_TV_HDD_Test_2_header);

        TextView hddTV1 = findViewById(R.id.act_fullResults_TV_HDD_Test_1);
        TextView hddTV2 = findViewById(R.id.act_fullResults_TV_HDD_Test_2);

        hddHTV1.setText("HDD write speed: ");
        hddHTV2.setText("HDD read speed: ");

        hddTV1.setText(new StringBuilder().append(String.format("%.2f", device.getHDDWriteSpeed())).append(" (MB/s)").toString());
        hddTV2.setText(new StringBuilder().append(String.format("%.2f", device.getHDDReadSpeed())).append(" (MB/s)").toString());
    }

    private void setCPUTV() {
        TextView cpuTV1 = findViewById(R.id.act_fullResults_TV_CPU_Test_1);
        TextView cpuTV2 = findViewById(R.id.act_fullResults_TV_CPU_Test_2);
        TextView cpuTV3 = findViewById(R.id.act_fullResults_TV_CPU_Test_3);
        TextView cpuTV4 = findViewById(R.id.act_fullResults_TV_CPU_Test_4);

        TextView cpuHTV1 = findViewById(R.id.act_fullResults_TV_CPU_Test_1_header);
        TextView cpuHTV2 = findViewById(R.id.act_fullResults_TV_CPU_Test_2_header);
        TextView cpuHTV3 = findViewById(R.id.act_fullResults_TV_CPU_Test_3_header);
        TextView cpuHTV4 = findViewById(R.id.act_fullResults_TV_CPU_Test_4_header);

        cpuHTV1.setText("Mersenne: ");
        cpuTV1.setText(new StringBuilder().append(String.format("%.2f",device.getScoreMersenne())));
        cpuHTV2.setText("Roots: ");
        cpuTV2.setText(new StringBuilder().append(String.format("%.2f",device.getScoreRoots())));
        cpuHTV3.setText("Hash: ");
        cpuTV3.setText(new StringBuilder().append(String.format("%.2f",device.getScoreHASH())));
        cpuHTV4.setText("Sieve of Atkin: ");
        cpuTV4.setText(new StringBuilder().append(String.format("%.2f",device.getScoreAtkin())));
    }
}
